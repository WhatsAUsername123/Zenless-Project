package utils

import okio.Path.Companion.toPath
import org.jetbrains.compose.resources.ExperimentalResourceApi
import zzzarchive.composeapp.generated.resources.Res

// Canonical slugs — must exactly match filenames in files/agents/*.gif
private val BUNDLED_AGENT_SLUGS: Set<String> = setOf(
    "ye_shunguang", "qingyi", "astra_yao", "remielle", "jane_doe", "velina",
    "vivian", "aria", "zhu_yuan", "corin", "billy", "ben", "lucy", "seth",
    "manato", "nekomata", "soldier_11", "pyrois", "piper", "koleda", "lycaon",
    "anton", "nicole", "soukaku", "pulchra", "panda", "anby", "rina", "caesar",
    "orphie", "miyabi", "zhao", "trigger", "yixuan", "sanby", "lucia"
)

// Map from the API's official/display name -> our slug, for any name that
// slugify() wouldn't turn into the right filename on its own.
// Add an entry here every time a mismatch is found; keys should be the
// EXACT string the API sends (case/punctuation as-is — normalizeName
// below handles casing/whitespace, so you don't need to pre-lowercase).
private val AGENT_NAME_ALIASES: Map<String, String> = mapOf(
    "Soldier 0 - Anby" to "sanby",
    "Orphie & Magus" to "orphie",
    "Pan Yinhu" to "panda"
)

private fun normalizeName(name: String): String =
    name.trim().lowercase()

private fun slugify(name: String): String =
    normalizeName(name).replace(Regex("[^a-z0-9]+"), "_").trim('_')

private fun localAgentGifResPath(agentName: String): String? {
    val normalized = normalizeName(agentName)

    // 1. Check alias table first (normalized key match)
    val aliasSlug = AGENT_NAME_ALIASES.entries
        .firstOrNull { normalizeName(it.key) == normalized }
        ?.value

    val slug = aliasSlug ?: slugify(agentName)

    if (slug !in BUNDLED_AGENT_SLUGS) {
        // Makes silent fallback failures visible in logcat instead of
        // just quietly showing the wrong image with no trail.
        println("resolveAgentImageUri: no bundled gif for agentName='$agentName' (tried slug='$slug')")
        return null
    }
    return "files/agents/$slug.gif"
}

@OptIn(ExperimentalResourceApi::class)
suspend fun resolveAgentImageUri(
    agentName: String,
    fallbackUrl: String,
    fileStorage: AppFileStorage
): String {
    val resPath = localAgentGifResPath(agentName) ?: return fallbackUrl
    val fileName = resPath.substringAfterLast('/')
    val cachedFile = "${fileStorage.cacheDirPath()}/agent_gifs/$fileName".toPath()

    if (!fileStorage.fileSystem.exists(cachedFile)) {
        try {
            val bytes = Res.readBytes(resPath)
            cachedFile.parent?.let { fileStorage.fileSystem.createDirectories(it) }
            fileStorage.fileSystem.write(cachedFile) {
                write(bytes, 0, bytes.size)
            }
        } catch (e: Exception) {
            println("resolveAgentImageUri: failed to load bundled resource '$resPath': ${e.message}")
            return fallbackUrl
        }
    }
    return "file://$cachedFile"
}
