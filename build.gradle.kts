import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.3"
	id("io.spring.dependency-management") version "1.0.10.RELEASE"
	id("org.jmailen.kotlinter") version "3.5.0"
	id("nu.studer.jooq") version "5.2"
	id("org.flywaydb.flyway") version "9.11.0"
	kotlin("jvm") version "1.5.21"
	kotlin("plugin.spring") version "1.5.21"
}

group = "com.stoneconvo"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation ("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("mysql:mysql-connector-java")
	jooqGenerator("mysql:mysql-connector-java")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

jooq {
	version.set("3.14.1")
	edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)

	configurations {
		create("main") {
			generateSchemaSourceOnCompilation.set(false)

			jooqConfiguration.apply {
				logging = org.jooq.meta.jaxb.Logging.DEBUG
				jdbc.apply {
					driver = "com.mysql.cj.jdbc.Driver"
					url = "jdbc:mysql://localhost:3306/stoneconvo_dev"
					user = "root"
					password = "root"
				}
				generator.apply {
					name = "org.jooq.codegen.DefaultGenerator"
					database.apply {
						name = "org.jooq.meta.mysql.MySQLDatabase"
						includes = ".*"
						excludes = ""
						inputSchema = "stoneconvo_dev"
					}
					generate.apply {
						isJavaTimeTypes = true
						isRecords = true
						isDaos = true
					}
					target.apply {
						packageName = "com.stoneconvo.codegen"
						isClean = true
					}
					strategy.name = "org.jooq.codegen.example.JPrefixGeneratorStrategy"
				}
			}
		}
	}
}

flyway {
	url = "jdbc:mysql://localhost:3306/stoneconvo_dev"
	user = "root"
	password = "root"
}

buildscript {
	// Enforcing the jOOQ configuration XML schema version
	configurations["classpath"].resolutionStrategy.eachDependency {
		if (requested.group == "org.jooq") {
			useVersion("3.12.4")
		}
	}
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
