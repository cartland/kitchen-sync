package architecture

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class ArchitectureCheckTask : DefaultTask() {
    init {
        group = "verification"
        description = "Checks architectural layer boundaries"
    }

    /**
     * Allowed module dependencies. Each module may only declare project(":xxx")
     * dependencies on the modules listed here. This enforces inward-only dependency flow:
     *   compose-app → viewmodel/presentation-* → usecase → domain
     *   data/data-local implement domain but are never depended on by usecase or domain
     */
    private val allowedDependencies: Map<String, Set<String>> = mapOf(
        "domain" to emptySet(),
        "usecase" to setOf("domain"),
        "data-local" to setOf("domain"),
        "data" to setOf("domain", "data-local"),
        "viewmodel" to setOf("domain", "usecase"),
        "presentation-core" to setOf("domain"),
        "presentation-feature" to setOf("domain", "presentation-core", "viewmodel"),
        "compose-app" to setOf(
            "domain",
            "usecase",
            "data",
            "data-local",
            "viewmodel",
            "presentation-core",
            "presentation-feature",
            "test-common",
        ),
        "test-common" to setOf("domain"),
    )

    @TaskAction
    fun check() {
        val violations = mutableListOf<String>()
        val rootProject = project.rootProject

        rootProject.subprojects.forEach { subproject ->
            val moduleName = subproject.name
            val allowed = allowedDependencies[moduleName]

            if (allowed == null) {
                logger.warn("checkArchitecture: unknown module '$moduleName' — skipping (add to whitelist)")
                return@forEach
            }

            subproject.configurations.forEach configLoop@{ config ->
                // Only check dependency-declaring configurations
                if (!isDependencyConfig(config.name)) return@configLoop

                config.dependencies
                    .filterIsInstance<org.gradle.api.artifacts.ProjectDependency>()
                    .forEach { dep ->
                        val depName = dep.dependencyProject.name
                        if (depName !in allowed) {
                            violations.add(
                                ":$moduleName → :$depName (config: ${config.name})",
                            )
                        }
                    }
            }
        }

        if (violations.isNotEmpty()) {
            val message = buildString {
                appendLine("Architecture check FAILED — illegal module dependencies:")
                violations.forEach { appendLine("  • $it") }
                appendLine()
                appendLine("Allowed dependencies are defined in ArchitectureCheckTask.")
                appendLine("Update the whitelist if this dependency is intentional.")
            }
            throw org.gradle.api.GradleException(message)
        }

        logger.lifecycle("checkArchitecture: all module dependencies within allowed boundaries ✓")
    }

    private fun isDependencyConfig(name: String): Boolean {
        return name == "commonMainImplementation" ||
            name == "commonMainApi" ||
            name == "implementation" ||
            name == "api"
    }
}
