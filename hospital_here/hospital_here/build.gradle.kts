plugins {
	java
	id("org.springframework.boot") version "3.3.2"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "com.team5"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()

}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-websocket:3.3.1")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	implementation("mysql:mysql-connector-java:8.0.26")
	runtimeOnly("com.mysql:mysql-connector-j")
	implementation("org.mapstruct:mapstruct:1.5.5.Final")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("io.jsonwebtoken:jjwt-api:0.11.2")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.2")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.2")
	/**
	 * 바로 아래 두 줄(oauth2) 이 소셜 로그인 관련 의존성
	 */
	implementation ("org.springframework.boot:spring-boot-starter-oauth2-client")
	implementation ("org.springframework.security:spring-security-oauth2-jose")
	implementation ("com.google.api-client:google-api-client:1.32.1")
	implementation ("com.google.api-client:google-api-client-gson:1.32.1")
	implementation ("com.google.oauth-client:google-oauth-client-jetty:1.32.1")
	implementation ("com.google.oauth-client:google-oauth-client:1.32.1")
	implementation ("com.google.http-client:google-http-client-jackson2:1.40.1")

}

tasks.withType<Test> {
	useJUnitPlatform()
}
