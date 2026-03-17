package importboundary

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class ImportBoundaryCheckTask : DefaultTask() {
    init {
        group = "verification"
        description = "Checks import boundary rules between modules"
    }

    @TaskAction
    fun check() {
        logger.lifecycle("checkImportBoundary: no rules configured yet — pass")
    }
}
