package utils

import okio.FileSystem
import java.io.File

class DesktopAppFileStorage : AppFileStorage {
    override val fileSystem: FileSystem = FileSystem.SYSTEM

    override fun cacheDirPath(): String {
        return File(System.getProperty("java.io.tmpdir")).absolutePath
    }
}
