package utils

import okio.FileSystem
import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

class IosAppFileStorage : AppFileStorage {
    override val fileSystem: FileSystem = FileSystem.SYSTEM

    override fun cacheDirPath(): String {
        val paths = NSFileManager.defaultManager.URLsForDirectory(NSCachesDirectory, NSUserDomainMask)
        return (paths.firstOrNull() as? platform.Foundation.NSURL)?.path ?: ""
    }
}
