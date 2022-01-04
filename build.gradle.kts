import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.6"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"

	id("nu.studer.jooq") version "5.2.1"
	id("org.flywaydb.flyway") version "8.2.3"

	val kotlinVer = "1.6.0"
	kotlin("jvm") version kotlinVer
	kotlin("plugin.spring") version kotlinVer
	kotlin("kapt") version kotlinVer
}

group = "edu.uoc.hagendazs"
version = "1.2.0"

java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}
buildscript {
	dependencies {
		val postgresVer = "42.3.1"
		classpath("org.postgresql:postgresql:$postgresVer")
	}
}

sourceSets.main {
	withConvention(org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class) {
		kotlin.srcDirs("src/main/kotlin", "src/generated")
	}
}

val springBootVer = "2.5.6"
val postgresVer = "42.3.1"

dependencies {
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:$springBootVer")

	//Security
	implementation("org.springframework.security.oauth", "spring-security-oauth2", "2.5.1.RELEASE")
	implementation("com.auth0", "java-jwt", "3.11.0")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server:$springBootVer")

	//Logging
	implementation("io.github.microutils:kotlin-logging:2.1.0")

	//Spring core dependencies
	implementation("org.springframework.boot:spring-boot-starter-web:$springBootVer")
	testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVer")
	implementation("org.springframework.boot:spring-boot-starter-validation:2.6.0")


	// Serialization / Deserialization
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.0")

	//Database
	implementation("org.flywaydb:flyway-core:8.0.2")
	implementation("org.springframework.boot:spring-boot-starter-jooq:$springBootVer")
	runtimeOnly("org.postgresql:postgresql:$postgresVer")

	//Language
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	//Gradle generators
	jooqGenerator("org.postgresql:postgresql:$postgresVer")

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


val dbUrl = "jdbc:postgresql://localhost:6432/postgres"
val dbUser = "postgres"
val dbPassword = "Pass2021!"

flyway {
	url = dbUrl
	user = dbUser
	password = dbPassword
	baselineOnMigrate = true
	locations = arrayOf("filesystem:src/main/resources/db/migration")
}

jooq {
	version.set("3.14.7")
	edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)

	configurations {
		create("postgres") {
			jooqConfiguration.apply {
				logging = org.jooq.meta.jaxb.Logging.WARN
				jdbc.apply {
					driver = "org.postgresql.Driver"
					url = dbUrl
					user = dbUser
					password = dbPassword
					properties.add(org.jooq.meta.jaxb.Property().withKey("PAGE_SIZE").withValue("2048"))
				}
				generator.apply {
					name = "org.jooq.codegen.KotlinGenerator"
					database.apply {
						name = "org.jooq.meta.postgres.PostgresDatabase"
						inputSchema = "public"
						forcedTypes.apply {
							forcedTypes.addAll(
								arrayOf(
									org.jooq.meta.jaxb.ForcedType()
										.withEnumConverter(true)
										.withUserType("edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.SystemLanguage")
										.withIncludeTypes("SystemLanguageType")
								)
							)
						}
					}
					generate.apply {
						isDeprecated = false
						isRecords = true
						isImmutablePojos = false
						isFluentSetters = true
					}
					target.apply {
						packageName = "edu.uoc.hagendazs.generated.jooq"
						directory = "src/generated"
					}
					strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
				}
			}
		}
	}
}