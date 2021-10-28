rootProject.name = "TbaKotlinMasterProject"

include(
    "api-model",
    "app",
    "boundary-db",
    "commons-ktor",
    "commons-lang",
    "commons-test",
    "domain-boundary",
    "domain-logic",
    "domain-model",
)

// https://docs.gradle.org/current/userguide/declaring_dependencies.html#sec:type-safe-project-accessors
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
