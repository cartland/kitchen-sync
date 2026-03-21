package screenshotcoverage

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import java.io.File

open class PreviewCoverageCheckTask : DefaultTask() {
    @TaskAction
    fun check() {
        val rootDir = project.rootDir

        // 1. Scan presentation modules for @Preview functions
        val previewModules = listOf("presentation-core", "presentation-feature")
        val previewFunctions = mutableSetOf<PreviewInfo>()
        for (module in previewModules) {
            val srcDir = File(rootDir, "$module/src")
            if (!srcDir.exists()) continue
            srcDir
                .walk()
                .filter { it.extension == "kt" }
                .forEach { file ->
                    val text = file.readText()
                    val regex = Regex(
                        """@Preview[^@]*@Composable\s+fun\s+(\w+Preview)\s*\(""",
                    )
                    regex.findAll(text).forEach { match ->
                        previewFunctions.add(
                            PreviewInfo(
                                name = match.groupValues[1],
                                file = file.relativeTo(rootDir).path,
                                module = module,
                            ),
                        )
                    }
                }
        }

        // 2. Scan screenshot test files for preview function calls
        val testDir = File(rootDir, "android-screenshot-tests/src/screenshotTest")
        val testedPreviews = mutableSetOf<String>()
        if (testDir.exists()) {
            testDir
                .walk()
                .filter { it.extension == "kt" }
                .forEach { file ->
                    val text = file.readText()
                    val importRegex = Regex("""import\s+[\w.]+\.(\w+Preview)\s""")
                    importRegex.findAll(text).forEach { match ->
                        testedPreviews.add(match.groupValues[1])
                    }
                }
        }

        // 3. Report gaps
        val uncovered = previewFunctions.filter { it.name !in testedPreviews }
        val covered = previewFunctions.filter { it.name in testedPreviews }

        // 4. Print summary and fail if uncovered
        val total = previewFunctions.size
        val coveredCount = covered.size
        val pct = if (total > 0) (coveredCount * 100 / total) else 100
        logger.lifecycle("checkPreviewCoverage: $coveredCount/$total ($pct%) previews have screenshot tests")
        if (uncovered.isNotEmpty()) {
            val msg = uncovered.joinToString("\n") { "  - ${it.name} (${it.file})" }
            throw GradleException(
                "Screenshot coverage gap: ${uncovered.size} preview(s) missing tests:\n$msg",
            )
        }
        if (total == 0) {
            logger.lifecycle("checkPreviewCoverage: no @Preview functions found in presentation modules (OK)")
        } else {
            logger.lifecycle("checkPreviewCoverage: all previews have screenshot tests ✓")
        }
    }
}

data class PreviewInfo(
    val name: String,
    val file: String,
    val module: String,
)
