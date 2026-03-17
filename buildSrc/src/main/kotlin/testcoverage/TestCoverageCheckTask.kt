package testcoverage

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class TestCoverageCheckTask : DefaultTask() {
    init {
        group = "verification"
        description = "Checks test coverage thresholds"
    }

    @TaskAction
    fun check() {
        logger.lifecycle("checkTestCoverage: no rules configured yet — pass")
    }
}
