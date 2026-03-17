package themelayer

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class ThemeLayerCheckTask : DefaultTask() {
    init {
        group = "verification"
        description = "Checks theme layer usage conventions"
    }

    @TaskAction
    fun check() {
        logger.lifecycle("checkThemeLayer: no rules configured yet — pass")
    }
}
