package datastoreguard

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class DataStoreSingletonCheckTask : DefaultTask() {
    init {
        group = "verification"
        description = "Checks that DataStore/Room @Provides methods have @Singleton scope"
    }

    /**
     * Return types that indicate a database provider method.
     * These methods MUST be annotated with @Singleton to prevent
     * multiple database instances which corrupt data silently.
     */
    private val guardedReturnTypes = setOf(
        "AppDatabase",
        "RoomDatabase",
    )

    private val providesPattern = Regex("""@Provides""")
    private val singletonPattern = Regex("""@Singleton""")
    private val funReturnPattern = Regex("""fun\s+\w+\s*\(.*\)\s*:\s*(\w+)""")

    @TaskAction
    fun check() {
        val violations = mutableListOf<String>()
        val rootProject = project.rootProject

        rootProject.subprojects.forEach { subproject ->
            val srcDir = File(rootProject.file(subproject.name), "src")
            if (!srcDir.exists()) return@forEach

            srcDir.walk()
                .filter { it.isFile && it.extension == "kt" }
                .forEach { file ->
                    checkFile(file, rootProject.projectDir, violations)
                }
        }

        if (violations.isNotEmpty()) {
            val message = buildString {
                appendLine("DataStore singleton check FAILED — @Provides without @Singleton:")
                violations.forEach { appendLine("  • $it") }
                appendLine()
                appendLine("Room/Database @Provides methods must be annotated with @Singleton")
                appendLine("to prevent multiple database instances.")
            }
            throw org.gradle.api.GradleException(message)
        }

        logger.lifecycle("checkDataStoreSingleton: all database providers are @Singleton ✓")
    }

    private fun checkFile(file: File, projectDir: File, violations: MutableList<String>) {
        val lines = file.readLines()
        val relativePath = file.relativeTo(projectDir)

        // Scan for @Provides methods that return a guarded type
        var i = 0
        while (i < lines.size) {
            val line = lines[i]

            // Look for @Provides annotation
            if (providesPattern.containsMatchIn(line)) {
                // Search forward for the function declaration (may be on same or next lines)
                val searchWindow = lines.subList(i, minOf(i + 5, lines.size)).joinToString(" ")
                val funMatch = funReturnPattern.find(searchWindow)

                if (funMatch != null) {
                    val returnType = funMatch.groupValues[1]
                    if (returnType in guardedReturnTypes) {
                        // Check backward for @Singleton in the annotation block
                        val annotationWindow = lines.subList(
                            maxOf(0, i - 5),
                            minOf(i + 5, lines.size),
                        ).joinToString("\n")

                        if (!singletonPattern.containsMatchIn(annotationWindow)) {
                            violations.add(
                                "$relativePath:${i + 1} — @Provides returning $returnType " +
                                    "is missing @Singleton annotation",
                            )
                        }
                    }
                }
            }
            i++
        }
    }
}
