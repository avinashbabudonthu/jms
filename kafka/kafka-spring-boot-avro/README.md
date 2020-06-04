# Spring Boot Kafka Avro Producer Consumer

## Requirement
* Spring Boot Kafka Avro Producer
* Spring Boot Kafka Avro Consumer


## Maven Command
```
mvn archetype:generate -DgroupId=com.kafka.spring.boot.avro -DartifactId=kafka-spring-boot-avro -Dversion=1.0 -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
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

## API
* Refer [files/.json](files/.json)

## Run from IDE
* Import project into IDE as Maven or Gradle project
* Execute [App.java](src/main/java/spring/boot/actuator/.java)

## Run using maven executive plugin
```
mvn clean compile exec:java
```

## Run using spring boot maven plugin
```
mvn clean compile spring-boot:run
```

## Run using spring boot gradle plugin
```
gradlew clean compileJava bootRun
```

## Create package using maven
```
mvn clean compile package
```

## Execute jar of Maven
```
java -jar target\.jar
```

## Create package using gradle
```
gradlew clean compileJava build
```

## Execute jar of Gradle
```
java -jar build\libs\-1.0.jar
```