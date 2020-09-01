import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.google.protobuf.gradle.*

plugins {
	// Spring
	id("org.springframework.boot") version "2.3.3.RELEASE"
	id("io.spring.dependency-management") version "1.0.10.RELEASE"

	// Kotlin
	kotlin("jvm") version "1.3.72"
	kotlin("plugin.spring") version "1.3.72"

	// Protobuf and Grpc
	id("com.google.protobuf") version "0.8.13"
}

group = "br.com.hugobenicio.springexamples"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	// Kotlin
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	// Spring Boot
	implementation("org.springframework.boot:spring-boot-starter-web")

	// Jackson (Json Serialization/Deserialization)
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	// GRPC
	// https://github.com/LogNet/grpc-spring-boot-starter
	// Auto-configures and runs the embedded gRPC server with @GRpcService-enabled beans as part of spring-boot application.
	implementation("io.github.lognet:grpc-spring-boot-starter:3.5.7")
	implementation("com.google.protobuf:protobuf-gradle-plugin:0.8.13")

	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}

kotlin {
	sourceSets["main"].apply {
		kotlin.srcDir("$projectDir/build/generated/source/proto/main/java")
	}
}

protobuf {
//	generatedFilesBaseDir = "$projectDir/src/generated"
	protoc {
		artifact = "com.google.protobuf:protoc:3.0.0"
	}
	plugins {
		// Optional: an artifact spec for a protoc plugin, with "grpc" as
		// the identifier, which can be referred to in the "plugins"
		// container of the "generateProtoTasks" closure.
		id("grpc") {
			artifact = "io.grpc:protoc-gen-grpc-java:1.15.1"
		}
	}
	generateProtoTasks {
		ofSourceSet("main").forEach {
			it.plugins {
				// Apply the "grpc" plugin whose spec is defined above, without options.
				id("grpc")
			}
		}
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}
