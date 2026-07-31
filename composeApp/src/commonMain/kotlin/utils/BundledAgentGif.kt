package utils

import okio.FileSystem
import okio.Path.Companion.toPath
import org.jetbrains.compose.resources.ExperimentalResourceApi
import zzzarchive.composeapp.generated.resources.Res

private val BUNDLED_AGENT_NAMES: Set<String> = setOf(
    "Ye Shunguang",
    "Qingyi",
    "Astra Yao",
    "Remielle",
    "Jane Doe",
    "Velina",
    "Vivian",
    "Aria",
    "Zhu Yuan"
)

private fun slugify(name: String): String =
    name.trim().lowercase().replace(Regex("[^a-z0-9]+"), "_").trim('_')

private fun localAgentGifResPath(agentName: String): String? =
    if (agentName in BUNDLED_AGENT_NAMES) "files/agents/${slugify(agentName)}.gif" else null

@OptIn(ExperimentalResourceApi::class)
suspend fun resolveAgentImageUri(
    agentName: String,
    fallbackUrl: String,
    fileStorage: AppFileStorage
): String {
    val resPath = localAgentGifResPath(agentName) ?: return fallbackUrl
    val fileName = resPath.substringAfterLast('/')
    val cachedFile = "${fileStorage.cacheDirPath()}/agent_gifs/$fileName".toPath()

    if (!FileSystem.SYSTEM.exists(cachedFile)) {
        val bytes = Res.readBytes(resPath)
        FileSystem.SYSTEM.createDirectories(cachedFile.parent!!)
        FileSystem.SYSTEM.write(cachedFile) { write(bytes) }
    }
    return "file://$cachedFile"
}
