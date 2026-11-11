package feature.buildanalysis

import feature.hoyolab.model.agent.MyAgentDetailEquip
import feature.hoyolab.model.agent.MyAgentDetailProperty
import feature.hoyolab.model.agent.MyAgentDriveProperty

// ---------------------------------------------------------------------
// Aggregate stats: MyAgentDetail.properties -> Map<StatType, Double>
// Uses `final` (already computed by HoYoLab: base + add). Matched by
// Chinese `name` since MyAgentDetailProperty.id looks like a plain list
// index (1, 2, ...), not a stable stat identifier like disc systemId is.
// ---------------------------------------------------------------------
private val propertyNameMap = mapOf(
    "生命值" to StatType.HP,
    "攻擊力" to StatType.ATK,
    "防禦力" to StatType.DEF,
    "暴擊率" to StatType.CRIT_RATE,
    "暴擊傷害" to StatType.CRIT_DMG,
    "異常精通" to StatType.ANOMALY_PROFICIENCY,
    "穿透率" to StatType.PEN_RATIO
    // TODO: confirm exact Chinese labels for Anomaly Mastery (異常掌控?),
    // Impact (衝擊力), and Energy Regen once seen in a real properties
    // list - only HP/ATK are in the stub, the rest are inferred from
    // labels seen elsewhere in this codebase's stub data.
)

object AggregateStatsMapper {
    fun toDomain(properties: List<MyAgentDetailProperty>): Map<StatType, Double> =
        properties.mapNotNull { prop ->
            val type = propertyNameMap[prop.name] ?: return@mapNotNull null
            val value = prop.final.replace("%", "").replace(",", "").trim().toDoubleOrNull()
                ?: return@mapNotNull null
            type to value
        }.toMap()
}

// ---------------------------------------------------------------------
// systemId -> StatType. Sourced from observed HoYoLab responses
// (MyAgentDetailEquipPlanResponse + MyAgentDetailEquipResponse stubs).
// Not officially documented - expand as new systemIds show up in real
// data. Unmapped IDs fall through to null and get skipped rather than
// crashing the analyzer.
// ---------------------------------------------------------------------
internal val systemIdMap = mapOf(
    111 to StatType.HP,
    121 to StatType.ATK,
    131 to StatType.DEF,
    201 to StatType.CRIT_RATE,
    211 to StatType.CRIT_DMG,
    232 to StatType.PEN_RATIO,
    312 to StatType.ANOMALY_PROFICIENCY
    // TODO: confirm systemIds for Anomaly Mastery, Impact, Energy Regen,
    // and elemental DMG% substats once seen on an actual disc response -
    // the weapon stub's "衝擊力"/Impact came through with systemId 0,
    // which looks like a placeholder rather than a real mapped ID.
)

data class ParsedStat(val type: StatType, val value: Double, val isPercent: Boolean)

data class DriveDiscEquip(
    val id: Int,
    val slot: Int?, // equipmentType, 1-6
    val level: Int,
    val mainStat: ParsedStat?,
    val subStats: List<ParsedStat>,
    val setId: Int?,
    val setName: String?,
    val setPiecesOwned: Int?
)

// ---------------------------------------------------------------------
// Adapts the app's EXISTING domain model (MyAgentDetailEquip) into
// DriveDiscEquip for the scorer - supersedes the earlier version that
// mapped from the raw MyAgentDetailEquipResponse. The app already does
// that raw->domain mapping via toMyAgentDetailEquip(), so this just
// adapts one domain shape to another.
// ---------------------------------------------------------------------
object EquipMapper {

    fun toDomain(equip: MyAgentDetailEquip): DriveDiscEquip = DriveDiscEquip(
        id = equip.id,
        slot = equip.equipmentType,
        level = equip.level,
        mainStat = equip.mainProperties.firstOrNull()?.let(::parseDriveProperty),
        subStats = equip.subProperties.mapNotNull(::parseDriveProperty),
        setId = (equip.equipSuit as? feature.hoyolab.model.agent.MyAgentEquipSuit.EquipSuit)?.suitId,
        setName = (equip.equipSuit as? feature.hoyolab.model.agent.MyAgentEquipSuit.EquipSuit)?.name,
        setPiecesOwned = (equip.equipSuit as? feature.hoyolab.model.agent.MyAgentEquipSuit.EquipSuit)?.own
    )

    private fun parseDriveProperty(prop: MyAgentDriveProperty): ParsedStat? {
        val type = systemIdMap[prop.systemId] ?: return null
        val isPercent = prop.base.contains("%")
        val value = prop.base.replace("%", "").replace(",", "").trim().toDoubleOrNull() ?: return null
        return ParsedStat(type, value, isPercent)
    }
}
