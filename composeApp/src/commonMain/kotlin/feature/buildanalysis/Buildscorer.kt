package feature.buildanalysis

// NOTE: ParsedStat, DriveDiscEquip, EquipMapper, and the systemId->StatType
// map now live in AggregateStatsMapper.kt, mapping from the app's existing
// domain model (MyAgentDetailEquip) instead of the raw HoYoLab response.

// ---------------------------------------------------------------------
// Scoring
// ---------------------------------------------------------------------

enum class RangeStatus { BELOW, IN_RANGE, ABOVE }

data class StatAssessment(
    val stat: StatType,
    val current: Double,
    val range: StatRange?,
    val status: RangeStatus
)

data class DiscScore(
    val disc: DriveDiscEquip,
    val score: Double,       // weighted sum of substats by priority tier; higher = better
    val offPriorityStats: List<StatType> // substats present that aren't in the priority list at all
)

data class BuildAnalysisResult(
    val overallScorePct: Double,        // 0-100, % of target ranges met/exceeded
    val statAssessments: List<StatAssessment>,
    val discScores: List<DiscScore>,    // sorted worst -> best
    val discToReplace: DriveDiscEquip?,
    val farmRecommendation: String
)

object BuildScorer {

    /**
     * @param currentStats aggregate character stats (from the agent detail
     *   summary block - HP/ATK/CRIT etc totals), NOT summed from discs here,
     *   since base agent stats + W-Engine also contribute to the total.
     * @param discs the 6 equipped drive discs for this agent.
     * @param profile parsed Prydwen build data for this agent.
     */
    fun analyze(
        currentStats: Map<StatType, Double>,
        discs: List<DriveDiscEquip>,
        profile: AgentBuildProfile
    ): BuildAnalysisResult {

        // --- 1. overall stat assessment vs target ranges ---
        val assessments = profile.targetRanges.map { range ->
            val current = currentStats[range.stat] ?: 0.0
            val status = when {
                current < range.min -> RangeStatus.BELOW
                range.max != null && current > range.max -> RangeStatus.ABOVE
                else -> RangeStatus.IN_RANGE
            }
            StatAssessment(range.stat, current, range, status)
        }
        val metCount = assessments.count { it.status != RangeStatus.BELOW }
        val overallScorePct = if (assessments.isEmpty()) 0.0
        else (metCount.toDouble() / assessments.size) * 100.0

        // --- 2. per-disc scoring using substat priority tiers ---
        val tierWeights = profile.substatPriority.tiers
            .mapIndexed { index, tier -> tier.associateWith { profile.substatPriority.tiers.size - index } }
            .fold(emptyMap<StatType, Int>()) { acc, m -> acc + m }

        val discScores = discs.map { disc ->
            val score = disc.subStats.sumOf { (tierWeights[it.type] ?: 0) * 1.0 }
            val offPriority = disc.subStats.map { it.type }.filterNot { tierWeights.containsKey(it) }
            DiscScore(disc, score, offPriority)
        }.sortedBy { it.score }

        val discToReplace = discScores.firstOrNull()?.disc

        // --- 3. farm recommendation: missing set piece, else weakest stat ---
        val bestSet = profile.recommendedSets.firstOrNull()
        val ownedSetCounts = discs.groupBy { it.setId }.mapValues { it.value.sumOf { d -> 1 } }
        val hasBestSet = bestSet != null &&
            discs.any { it.setName == bestSet.name && (it.setPiecesOwned ?: 0) >= bestSet.pieces }

        val farmRecommendation = when {
            bestSet != null && !hasBestSet ->
                "Farm ${bestSet.name} (${bestSet.pieces}-PC) - your current set doesn't match the recommended build"
            else -> {
                val weakest = assessments.filter { it.status == RangeStatus.BELOW }
                    .minByOrNull { (it.range?.min ?: 0.0) - it.current }
                weakest?.let { "Farm for more ${it.stat} - currently ${it.current}, target ${it.range?.min}+" }
                    ?: "Build is hitting all target stat ranges - focus on refining substat rolls"
            }
        }

        return BuildAnalysisResult(
            overallScorePct = overallScorePct,
            statAssessments = assessments,
            discScores = discScores,
            discToReplace = discToReplace,
            farmRecommendation = farmRecommendation
        )
    }
}
