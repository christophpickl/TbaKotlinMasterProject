rootProject.name = "TbaKotlinMasterProject"

include(
    "app",
    "api-model",
    "commons-ktor",
    "commons-test",
    "domain-logic",
    "domain-model",
)

// https://docs.gradle.org/current/userguide/declaring_dependencies.html#sec:type-safe-project-accessors
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
