# Spring Boot 2 Kafka Producer

## Requirement


## Maven Command
```
mvn archetype:generate -DgroupId=com.spring.boot2.kafka.producer -DartifactId=spring-boot2-kafka-producer -Dversion=1.0 -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false 
```

## Gradle Command
```
gradle init --type pom
```

## Steps
* Dependencies - Refer [pom.xml](pom.xml) or [build.gradle](build.gradle)

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

## References
* KafkaTemplate - https://docs.spring.io/spring-kafka/reference/html/#sending-messages