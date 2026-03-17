package namingconvention

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class NamingConventionCheckTask : DefaultTask() {
    init {
        group = "verification"
        description = "Checks naming convention compliance"
    }

    @TaskAction
    fun check() {
        logger.lifecycle("checkNamingConventions: no rules configured yet — pass")
    }
}
