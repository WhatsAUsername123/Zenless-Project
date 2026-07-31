package utils

import android.content.Context

class AndroidAppFileStorage(private val context: Context) : AppFileStorage {
    override fun cacheDirPath(): String = context.cacheDir.path
}
