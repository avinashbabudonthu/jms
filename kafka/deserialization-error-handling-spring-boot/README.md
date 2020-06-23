# Deserialization Error Handling in Kafka with Spring Boot

## Requirement
* Error handling while deserializing messages from Kafka Topic in spring boot

## Maven Command
```
mvn archetype:generate -DgroupId=com.deserialization.error.handling.spring.boot -DartifactId=deserialization-error-handling-spring-boot -Dversion=1.0 -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```

## Gradle Command
```
gradle init --type pom
```

## Steps
* Dependencies refer in [pom.xml](pom.xml) or [build.gradle](build.gradle)
* Add `avro-maven-plugin` plugin
* Generate pojo classes as per schema [user.avsc](src/main/resources/avro/user.avsc)
```
mvn clean compile package
```
* We can see model classes getting generated in the package [com.app.avro.model](src/main/java/com/app/avro/model)
	* Model classes are generated in this package because following code in `avro-maven-plugin`
```
<configuration>
	<sourceDirectory>${project.basedir}/src/main/resources/avro</sourceDirectory>
	<outputDirectory>${project.basedir}/src/main/java/</outputDirectory>
	<stringType>String</stringType>
</configuration>
```

## Replicate issue:
* Comment [ConsumerComponent.java](src/main/java/com/app/kafka/ConsumerComponent.java) code
* Start app using `avro` profiles
```
-Dspring.profiles.active=avro
```
![picture](images/1.jpg)
* Call `send-avro-message` API - sends 10 messages to topic
* Stop app
* Start app using `string` profile
```
-Dspring.profiles.active=string
```
* Call `send-string-message` API - sends 10 messages to topic
* Stop app
* Uncomment [ConsumerComponent.java](src/main/java/com/app/kafka/ConsumerComponent.java) code
* Start app using `avro` profiles
```
-Dspring.profiles.active=avro
```