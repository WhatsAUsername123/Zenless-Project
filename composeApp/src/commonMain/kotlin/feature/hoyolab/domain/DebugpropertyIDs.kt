package feature.hoyolab.domain

import feature.hoyolab.model.agent.MyAgentDetail

fun debugLogPropertyIds(detail: MyAgentDetail) {
    println("=== ${detail.name} aggregate properties ===")
    detail.properties.forEach { p ->
        println("id=${p.id}  name=${p.name}  base=${p.base}  add=${p.add}  final=${p.final}")
    }

    println("=== ${detail.name} equip properties ===")
    detail.equip.forEach { equip ->
        println("--- disc slot=${equip.equipmentType} name=${equip.name} ---")

        println("  main:")
        equip.mainProperties.forEach { p ->
            println("    systemId=${p.systemId}  name=${p.name}  base=${p.base}")
        }

        println("  sub:")
        equip.subProperties.forEach { p ->
            println(
                "    systemId=${p.systemId}  name=${p.name}  base=${p.base}  level=${p.level}"
            )
        }
    }

    val weapon = detail.weapon
    if (weapon is feature.hoyolab.model.agent.MyAgentDetailWeapon.MyAgentWeapon) {
        println("=== ${detail.name} weapon properties ===")

        weapon.mainProperties.forEach { p ->
            println("  main systemId=${p.systemId}  name=${p.name}  base=${p.base}")
        }

        weapon.properties.forEach { p ->
            println("  sub  systemId=${p.systemId}  name=${p.name}  base=${p.base}")
        }
    }
}
