import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version Versions.Plugins.Kotlin
	id("io.kotest") version Versions.Plugins.Kotest
	id("com.github.ben-manes.versions") version Versions.Plugins.Versions // ./gradlew dependencyUpdates
}

allprojects {
	repositories {
		mavenCentral()
	}
}

subprojects {
	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "io.kotest")
	apply(plugin = "com.github.ben-manes.versions")
	
	dependencies {
		implementation(kotlin("stdlib-jdk8"))
		implementation(kotlin("reflect"))
		implementation(Dependencies.Logging.MuLogging)
		
		if(!setOf("api-model", "domain-model").contains(project.name)) {
			implementation(Dependencies.Koin.Core)
		}
		
		testImplementation(Dependencies.Kotest.RunnerJunit5)
		testImplementation(Dependencies.Kotest.AssertionsCoreJvm)
		testImplementation(Dependencies.Mockk)
	}
	
	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "11"
		}
	}
	
	tasks.withType<Test> {
		useJUnitPlatform()
	}
	
	tasks.withType<DependencyUpdatesTask> {
		val rejectPatterns = listOf("alpha", "beta", "EAP", "RC", "m").map { it.toLowerCase() }
		rejectVersionIf {
			val version = candidate.version.toLowerCase()
			rejectPatterns.any { version.contains(it) }
		}
	}
}
