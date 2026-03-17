package architecture

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class ArchitectureCheckTask : DefaultTask() {
    init {
        group = "verification"
        description = "Checks architectural layer boundaries"
    }

    @TaskAction
    fun check() {
        logger.lifecycle("checkArchitecture: no rules configured yet — pass")
    }
}
