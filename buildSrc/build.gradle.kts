plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

gradlePlugin {
    plugins {
        register("architecture-check") {
            id = "kitchen-sync.architecture-check"
            implementationClass = "architecture.ArchitecturePlugin"
        }
        register("theme-layer-check") {
            id = "kitchen-sync.theme-layer-check"
            implementationClass = "themelayer.ThemeLayerPlugin"
        }
        register("test-coverage-check") {
            id = "kitchen-sync.test-coverage-check"
            implementationClass = "testcoverage.TestCoveragePlugin"
        }
        register("naming-convention-check") {
            id = "kitchen-sync.naming-convention-check"
            implementationClass = "namingconvention.NamingConventionPlugin"
        }
        register("import-boundary-check") {
            id = "kitchen-sync.import-boundary-check"
            implementationClass = "importboundary.ImportBoundaryPlugin"
        }
    }
}
