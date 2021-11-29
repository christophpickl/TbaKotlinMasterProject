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

		if(project.name != "commons-test") {
			testImplementation(project(":commons:commons-test"))
		}
	}
	
	tasks.withType<KotlinCompile>().configureEach {
		kotlinOptions {
			freeCompilerArgs = listOf(
                "-Xjsr305=strict",
//                "-opt-in=kotlinx.coroutines.DelicateCoroutinesApi"
                "-Xuse-experimental=kotlinx.coroutines.DelicateCoroutinesApi",
                "-Xuse-experimental=kotlin.time.ExperimentalTime"
            )
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

	if(setOf("domain-model", "boundary-db").contains(project.name)) {
		configurations {
			create("test")
		}
		
		tasks.register<Jar>("testArchive") {
			archiveBaseName.set("${project.name}-test")
			from(project.the<SourceSetContainer>()["test"].output)
		}
		
		artifacts {
			add("test", tasks["testArchive"])
		}
	}
}
