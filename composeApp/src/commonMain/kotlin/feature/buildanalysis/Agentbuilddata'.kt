package feature.buildanalysis

import kotlinx.serialization.Serializable

// ---------------------------------------------------------------------
// DTO layer - mirrors scrape_prydwen.py's JSON output exactly.
// Lives in `network`, deserialized straight from the fetched file.
// ---------------------------------------------------------------------

@Serializable
data class AgentBuildDataDto(
    val slug: String,
    val source: String,
    val scrapedAt: String,
    val mainStatPriority: Map<String, String> = emptyMap(),   // "disk_4" -> "CRIT Rate% >= ATK%"
    val substatPriority: String? = null,                       // "CRIT RATE (Until 80%) >= CRIT DMG = ATK% > ..."
    val targetStatRanges: Map<String, String> = emptyMap(),    // "CRIT RATE" -> "75-95%", "ATK" -> "2500 - 3600+"
    val discSets: List<DiscSetDto> = emptyList()
)

@Serializable
data class DiscSetDto(
    val name: String,
    val pieces: Int,
    val scorePct: Double
)

// ---------------------------------------------------------------------
// Domain layer - parsed into something the scorer can actually compute
// against. Lives in `feature/buildanalysis`. Map DTO -> domain once,
// right after fetch/cache load, so the scoring code never touches raw
// strings.
// ---------------------------------------------------------------------

enum class StatType {
    HP, ATK, DEF, CRIT_RATE, CRIT_DMG, ANOMALY_MASTERY, ANOMALY_PROFICIENCY,
    PEN_RATIO, IMPACT, ENERGY_REGEN
}

/** A target range for one stat, e.g. CRIT_RATE 75.0..95.0 */
data class StatRange(
    val stat: StatType,
    val min: Double,
    val max: Double?  // null when the source says "10000+" with no upper bound
)

/** Ordered substat priority, highest first. Ties (">=" or "=") share a tier. */
data class SubstatPriority(
    val tiers: List<List<StatType>>  // tiers[0] = highest priority tier
)

data class AgentBuildProfile(
    val slug: String,
    val mainStatPriority: Map<String, List<StatType>>, // disc slot number -> ordered candidates
    val substatPriority: SubstatPriority,
    val targetRanges: List<StatRange>,
    val recommendedSets: List<DiscSetDto>, // keep DTO here, it's just display data
    val scrapedAt: String
)

// ---------------------------------------------------------------------
// Parsing: raw strings -> domain. Isolated here so when Prydwen's
// wording drifts (e.g. "Until 80%" vs "up to 80%") there's one place
// to fix it, not scattered regexes through the scorer.
// ---------------------------------------------------------------------

object BuildDataParser {

    private val statNameMap = mapOf(
        "hp" to StatType.HP,
        "atk" to StatType.ATK,
        "atk%" to StatType.ATK,
        "def" to StatType.DEF,
        "crit rate" to StatType.CRIT_RATE,
        "crit dmg" to StatType.CRIT_DMG,
        "anomaly mastery" to StatType.ANOMALY_MASTERY,
        "anomaly proficiency" to StatType.ANOMALY_PROFICIENCY,
        "pen" to StatType.PEN_RATIO,
        "pen ratio" to StatType.PEN_RATIO,
        "impact" to StatType.IMPACT,
        "energy regen" to StatType.ENERGY_REGEN
    )

    fun toDomain(dto: AgentBuildDataDto): AgentBuildProfile = AgentBuildProfile(
        slug = dto.slug,
        mainStatPriority = dto.mainStatPriority.mapValues { (_, rule) -> parseStatOrder(rule) },
        substatPriority = parseSubstatPriority(dto.substatPriority.orEmpty()),
        targetRanges = dto.targetStatRanges.mapNotNull { (name, raw) -> parseRange(name, raw) },
        recommendedSets = dto.discSets.sortedByDescending { it.scorePct },
        scrapedAt = dto.scrapedAt
    )

    /** "CRIT Rate% >= ATK%" -> [CRIT_RATE, ATK], order preserved */
    private fun parseStatOrder(rule: String): List<StatType> =
        rule.split(">=", "=", ">")
            .mapNotNull { lookupStat(it) }

    /** "CRIT RATE (Until 80%) >= CRIT DMG = ATK% > Anomaly Proficiency = ATK = PEN" -> tiers */
    private fun parseSubstatPriority(raw: String): SubstatPriority {
        if (raw.isBlank()) return SubstatPriority(emptyList())
        val tiers = raw.split(">").map { tierGroup ->
            tierGroup.split("=").mapNotNull { lookupStat(it) }
        }.filter { it.isNotEmpty() }
        return SubstatPriority(tiers)
    }

    /** "75-95%" -> 75.0..95.0 ; "10000+" -> 10000.0..null ; "2500 - 3600+" -> 2500.0..3600.0 */
    private fun parseRange(name: String, raw: String): StatRange? {
        val stat = lookupStat(name) ?: return null
        val nums = Regex("""[\d.]+""").findAll(raw).map { it.value.toDouble() }.toList()
        return when {
            nums.isEmpty() -> null
            nums.size == 1 -> StatRange(stat, nums[0], if (raw.contains("+")) null else nums[0])
            else -> StatRange(stat, nums[0], nums[1])
        }
    }

    private fun lookupStat(raw: String): StatType? {
        val cleaned = raw.trim()
            .lowercase()
            .replace(Regex("""\(.*?\)"""), "") // strip "(Until 80%)" annotations
            .replace("%", "")
            .trim()
        return statNameMap[cleaned]
    }
}
