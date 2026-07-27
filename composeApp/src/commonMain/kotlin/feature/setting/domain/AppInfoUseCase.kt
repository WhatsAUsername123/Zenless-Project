/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package feature.setting.domain

import com.mrfatworm.zzzarchive.ZzzConfig
import utils.getPlatform

class AppInfoUseCase {
    fun getAppVersion(): String = ZzzConfig.VERSION

    fun getDeviceInfo(): String = getPlatform().deviceName

    fun getDeviceOs(): String = getPlatform().operatingSystemName
}
