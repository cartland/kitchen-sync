package importboundary

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class ImportBoundaryCheckTask : DefaultTask() {
    init {
        group = "verification"
        description = "Checks import boundary rules between modules"
    }

    /**
     * Deny-list: imports that specific modules must never contain.
     * Key = module name, Value = list of forbidden import prefixes.
     */
    private val denyList: Map<String, List<String>> = mapOf(
        "domain" to listOf(
            "androidx.room",
            "com.google.firebase",
            "com.chriscartland.kitchensync.data.",
            "com.chriscartland.kitchensync.datalocal.",
            "com.chriscartland.kitchensync.presentation",
            "com.chriscartland.kitchensync.viewmodel",
        ),
        "usecase" to listOf(
            "androidx.room",
            "com.google.firebase",
            "com.chriscartland.kitchensync.datalocal.",
            "com.chriscartland.kitchensync.presentation",
            "com.chriscartland.kitchensync.viewmodel",
            "androidx.compose",
        ),
        "data-local" to listOf(
            "com.chriscartland.kitchensync.data.",
            "com.chriscartland.kitchensync.presentation",
            "com.chriscartland.kitchensync.viewmodel",
            "com.chriscartland.kitchensync.usecase",
            "androidx.compose",
        ),
        "data" to listOf(
            "com.chriscartland.kitchensync.presentation",
            "com.chriscartland.kitchensync.viewmodel",
            "com.chriscartland.kitchensync.usecase",
            "androidx.compose",
        ),
    )

    private val importPattern = Regex("""^\s*import\s+(\S+)""")

    @TaskAction
    fun check() {
        val violations = mutableListOf<String>()
        val rootProject = project.rootProject

        denyList.forEach { (moduleName, forbiddenPrefixes) ->
            val moduleDir = rootProject.file(moduleName)
            if (!moduleDir.exists()) return@forEach

            val srcDir = File(moduleDir, "src")
            if (!srcDir.exists()) return@forEach

            srcDir.walk()
                .filter { it.isFile && it.extension == "kt" }
                .forEach { file ->
                    file.useLines { lines ->
                        lines.forEachIndexed { lineIndex, line ->
                            val match = importPattern.find(line) ?: return@forEachIndexed
                            val importPath = match.groupValues[1]

                            forbiddenPrefixes.forEach { prefix ->
                                if (importPath.startsWith(prefix)) {
                                    val relativePath = file.relativeTo(rootProject.projectDir)
                                    violations.add(
                                        "$relativePath:${lineIndex + 1} — " +
                                            ":$moduleName imports '$importPath' " +
                                            "(forbidden prefix: $prefix)",
                                    )
                                }
                            }
                        }
                    }
                }
        }

        if (violations.isNotEmpty()) {
            val message = buildString {
                appendLine("Import boundary check FAILED — forbidden imports found:")
                violations.forEach { appendLine("  • $it") }
                appendLine()
                appendLine("Deny-list rules are defined in ImportBoundaryCheckTask.")
            }
            throw org.gradle.api.GradleException(message)
        }

        logger.lifecycle("checkImportBoundary: no forbidden imports found ✓")
    }
}
