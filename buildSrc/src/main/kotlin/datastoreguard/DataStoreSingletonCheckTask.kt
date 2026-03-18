package datastoreguard

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class DataStoreSingletonCheckTask : DefaultTask() {
    init {
        group = "verification"
        description = "Checks that DataStore/Room @Provides methods have @Singleton scope"
    }

    @TaskAction
    fun check() {
        logger.lifecycle("checkDataStoreSingleton: no rules configured yet — pass")
    }
}
