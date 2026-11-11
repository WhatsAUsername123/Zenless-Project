package com.mrfatworm.zzzarchive

import ZzzArchiveApp
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import di.initKoin
import feature.setting.data.PreferencesRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import utils.changePlatformLanguage

class MainActivity : ComponentActivity() {

    private val preferencesRepository: PreferencesRepository by inject()

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        lifecycleScope.launch {
            val langCode = preferencesRepository.getLanguageCode().firstOrNull()
            if (langCode != null) {
                changePlatformLanguage(langCode)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            ZzzArchiveApp()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    // Initialize Koin for Android Studio Preview if KoinApplication has not been started
    val context = LocalContext.current
    if (GlobalContext.getOrNull() == null) {
        initKoin {
            androidContext(context)
        }
    }
    ZzzArchiveApp()
}
