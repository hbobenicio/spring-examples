import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.google.protobuf.gradle.*

buildscript {
	repositories {
		mavenCentral()
	}
	dependencies {
		// https://github.com/google/protobuf-gradle-plugin
		// The Gradle plugin that compiles Protocol Buffer (aka. Protobuf) definition files (*.proto) in your project.
		classpath("com.google.protobuf:protobuf-gradle-plugin:0.8.13")
	}
}

plugins {
	id("org.springframework.boot") version "2.3.3.RELEASE"
	id("io.spring.dependency-management") version "1.0.10.RELEASE"
	kotlin("jvm") version "1.3.72"
	kotlin("plugin.spring") version "1.3.72"
	id("com.google.protobuf") version "0.8.13"
}

group = "br.com.hugobenicio.springexamples"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	// https://github.com/LogNet/grpc-spring-boot-starter
	// Auto-configures and runs the embedded gRPC server with @GRpcService-enabled beans as part of spring-boot application.
	implementation("io.github.lognet:grpc-spring-boot-starter:3.5.7")

	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}

protobuf {
	protoc {
		artifact = "com.google.protobuf:protoc:3.0.0"
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
