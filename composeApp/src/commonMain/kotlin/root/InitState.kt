package root

data class InitState(
    val isDark: Boolean = true,
    val uiScale: Float = 1f,
    val fontScale: Float = 1f,
    val appVersion: String = "",
    val isLoading: Boolean = true
)
