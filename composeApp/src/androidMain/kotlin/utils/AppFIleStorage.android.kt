package utils

import android.content.Context
import okio.FileSystem

class AndroidAppFileStorage(private val context: Context) : AppFileStorage {
    override val fileSystem: FileSystem = FileSystem.SYSTEM
    override fun cacheDirPath(): String = context.cacheDir.path
}
