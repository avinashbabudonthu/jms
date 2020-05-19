# Spring Boot 2 Kafka Producer

## Requirement
* Write Spring Boot Kafka Producer

## Pre Requisites
* Zookeeper should be running in local
* Kafka should be running in local
* For this project I am running kafka cluster with 3 brokers

## Maven Command
```
mvn archetype:generate -DgroupId=com.spring.boot2.kafka.producer -DartifactId=spring-boot2-kafka-producer -Dversion=1.0 -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false 
```

## Gradle Command
```
gradle init --type pom
```

## Dependencies
* Add `spring-kafka`, `spring-kafka-test` dependencies
* Dependencies - Refer [pom.xml](pom.xml) or [build.gradle](build.gradle)

## Properties
* Add kafka properties in [application.yml](src/main/resources/application.yml)

## API
* Refer [files/spring-boot2-kafka-producer.postman_collection.json](files/spring-boot2-kafka-producer.postman_collection.json)

## Run from IDE
* Import project into IDE as Maven or Gradle project
* Execute [App.java](src/main/java/com/app/App.java)

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
java -jar target\spring-boot2-kafka-producer.jar
```

## Create package using gradle
```
gradlew clean compileJava build
```

## Execute jar of Gradle
```
java -jar build\libs\spring-boot2-kafka-producer-1.0.jar
```

## Classes
* [AppConfig.java](src/main/java/com/app/config/AppConfig.java): If we want to create topic on application start up then uncomment method `newTopic()`
* [AppController.java](src/main/java/com/app/controller/AppController.java): Controller class for all APIs
* [KafkaProducer.java](src/main/java/com/app/service/KafkaProducer.java): Kafka producer to send messages to kafka
	* Refer methods in this class to send messages in different ways
	
## References
* KafkaTemplate - https://docs.spring.io/spring-kafka/reference/html/#sending-messages