# Kafka Core Java Example

## Requirement


## Create project using maven
```
mvn archetype:generate -DgroupId=com.jms -DartifactId=kafka-core-java -Dversion=1.0 -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```

## Add Gradle. Execute from kafka-core-java folder
```
gradle init --type pom
```

## Steps
* Dependencies - Refer [pom.xml](pom.xml) or [build.gradle](build.gradle)

## Examples
* Producer to send message to Kafka Topic - [Producer.java](src/test/java/com/producer/Producer.java)
* Consumer to receive message to Kafka Topic - [Consumer.java](src/test/java/com/consumer/Consumer.java)
