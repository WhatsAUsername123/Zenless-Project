package utils

import okio.FileSystem

interface AppFileStorage {
    val fileSystem: FileSystem
    fun cacheDirPath(): String
}
