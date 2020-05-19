# Spring Boot 2 Kafka Consumer

## Requirement
* Write spring boot 2 kafka consumer

## Maven Command
```
mvn archetype:generate -DgroupId=com.spring.boot2.kafka.consumer -DartifactId=spring-boot2-kafka-consumer -Dversion=1.0 -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
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

## Run from IDE
* Import project into IDE as Maven or Gradle project
* Execute [App.java](src/main/java/com/app/App.java)

## Steps
* Create [AppConfig.java](src/main/java/com/app/config/AppConfig.java) class
	* Add `@EnableKafka` annotation at class level
* Create [KafkaConsumer.java](src/main/java/com/app/component/KafkaConsumer.java) class
	* Add `@Component` annotation at class level
	* Add `onMessage` method
		* Add `@KafkaListener(topics = { "students-topic" })` annotation to method
* Start Zookeeper
* Start kafka
* Start our appliation
	* Execute [App.java](src/main/java/com/app/App.java)
* Send message from kafka topic using producer - Refer https://github.com/avinashbabudonthu/jms/tree/master/kafka/spring-boot2-kafka-producer
* Once message sent to kafka topic, our consumer receives the message
	* We can see log from `onMessage` method in [KafkaConsumer.java](src/main/java/com/app/component/KafkaConsumer.java) class

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
java -jar target\spring-boot2-kafka-consumer.jar
```

## Create package using gradle
```
gradlew clean compileJava build
```

## Execute jar of Gradle
```
java -jar build\libs\spring-boot2-kafka-consumer-1.0.jar
```

## References
* https://docs.spring.io/spring-kafka/reference/html/#kafka-listener-annotation
