# Kafka Core Java Avro

## Requirement
* Send Avro message to Kafka topic

## Maven Command
```

```

## Gradle Command
```
gradle init --type pom
```

## Steps
* Dependencies - Refer [pom.xml](pom.xml) or [build.gradle](build.gradle)

## Steps
* Start [Kafka Confluent](https://github.com/avinashbabudonthu/jms/blob/master/kafka/kafka-confluent-platform-setup.md#kafka-confluent-platform-setup-in-windows-machine-using-docker) docker container in local
* Create [avro schema](src/main/resources/avro/user.avsc)
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
* [Producer](src/main/java/com/avro/Producer.java)