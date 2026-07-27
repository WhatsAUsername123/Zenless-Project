/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package feature.hoyolab.model

enum class ServersList(val localName: String, val region: String) {
    ASIA(localName = "Asia", region = "prod_gf_jp"),
    AMERICA(localName = "America", region = "prod_gf_us"),
    EUROPE(localName = "Europe", region = "prod_gf_eu"),
    TW_HK_MO(localName = "TW,HK,MO", region = "prod_gf_sg"),
    None(localName = "", region = "")
}
