# Kafka Notes

## Basics
* Message retaining
	* In traditional messaging system has transient message persistance. Means once message is consumed by consumer it will be deleted from the message broker
	* In the case of Kafka event will be saved in the file system where kafka is installed. Events are retained for certain time
* All the events in kafka are immutable. Means once message sent to kafka then it cannot be altered
* Any consumer who has access to broker can read the message
* Kafka is distributed streaming system
* Kafka is streaming system. Not a just messaging system

## Kafka Terminology and Client APIs
* Kafka Cluster: consists of multiple brokers
* In order to manage multiple brokers we need Zookeeper
	* Zookeeper keeps track of health of brokers and manage the cluster for you
* `Broker` is what all kafka clients interact with
* Kafka Producer
	* Client to kafka broker
	* Produce new data to kafka
* Kafka consumer
	* Consume messages from kafka broker
* Client APIs of kafka
	* Kafka connect
		* Source connector: Used to pull the data from external data source such as database, file system, elastic search into kafka topic
		* Sink connector: opposite of source connector
	* Kafka streams API
		* Take the data from kafka and perform simple to complex transformations and put it back to kafka
* Finally kafka client APIs as described above
	* Producer API
	* Consumer API
	* Connect API
	* Streams API
	
## Kafka Topics and Partitions
### Topic
* Kafka topic is like table in database
* Topic live inside kafka broker
* Kafka clients uses topic name to produce and consume messages
* Kafka consumers generally poll continuously for new messsages
### Partitions
* Where message actually located inside topic
* Each topic can have one or more partitions
* Partitions have effect on scalable message consumption
* Each partition is ordered, immutable, sequence of records
* Each record has number associated with it called `offset`
* `offset` is generated when record is published to kafka topic
* Each partition is independent of each other
* Ordering is guaranteed only at partition level
* Partitions continuously grow as new records produced and offset gets incremented one by one
* All records are persisted in commit log in file system where kafka is installed
* Producer can control to which partition the message can go

## Sending message without key
* When we send message to topic, it will go through `Partitioner`
* If key is not present with message `partitioner` will follow round robin technique
* Partitioner will send messages to different partitions in round robin fashion

## Sending message with key
* Every message will have a key
* We can use any datatype for key. General practice is to use String
* When message contains key then `partitioner` will apply hashing technique to determine partition
* Multiple messages can be sent with same key. Then they all goes to same partition

## Consumer Offset
* Any message produced to topic will have unique id called `offset`
* Consumers will have 3 options when reading messages from topic
	* from-beginning: all messages from starting
	* latest: read only messages that came after consumer started. Default value
	* specific offset: read messages in the topic by passing `offset` values from consumer. This option can only be done programmatically
* Consumer read each message and after reading message offset incremented by 1. Once all the poll records are read then consumer commits the offset to the topic `__consumer_offsets` with the group id. Now consumer is down and brought up after some time. By this time producer produced some more messages. Now consumer know where to start to consume message with the value present in `__consumer_offsets` topic with the group id

## Consumer Groups
* Group id plays major role when it comes to scalable message consumption
* Different applications need to have unique group id
* who manages consumer group?
	* Kafka broker manages the consumer groups
	* Kafka broker also acts as group co-ordinator

## Commit Log and Retention Policy
### Commit Log
* When producer sends a message, first it reaches topic and then record written to file system in the machine. Record always writtent to file system as bytes
* In file system where that records needs to be written is configured property `log.dirs` property in `kafka-directory/config/server.properties` file
* File will be present in that location with extension `.log`
* Each partition will have it's own log
* After messages are written to log files then records are committed. Consumer who is polling records can only see records which are committed to the file system
* As new records produced they get appended to log file
### Retention Policy
* Determines how long the message is going to be retained?
* Retention policy is configured using property `log.retention.hours` in `kafka-directory/config/server.properties` file. Default retention period is `168 hours` that is `7 days`

## Kafka as distributed streaming platform
### What is distributed system
* collection of systems work together to deliver the functionality
#### Charateristics of distributed system
* Availability and fault tolerance
* Reliable work distribution. client requests equally distributed among the available systems
* Easily scalable
* Handling concurrency is easy
### Single point of failure
* All Producer and consumers interact with kafka with Broker
* If broker goes down then all producer and consumer requests will fail
* This will become single point of failure
### How to solve single point of failure
* Let's say we have cluster of 3 brokers
* Cluster will be managed by zoo-keeper
* All brokers send heart beat to zoo-keeper at regular intervals to ensure state of kafka broker is healthy to serve client requests
* Client requests distributed among 3 brokers. If any of the broker goes down then zoo-keeper gets notified then all client requests will be routed to other available brokers

## Setting up kafka cluster in local with 3 brokers
* In order to start multiple kafka brokers, we need to have different `server.properties` file with each broker. This new `server.properties` should have unique values compared to other `server.properties`
```
broker.id=1
listeners=PLAINTEXT://localhost:9093
log.dirs=C:/softwares/kafka/logs1
auto.create.topics.enable=false(optional)
```
* Start `zoo-keeper`
```
\zoo-keeper\apache-zookeeper-3.6.1-bin.tar\apache-zookeeper-3.6.1-bin\apache-zookeeper-3.6.1-bin\bin\zkServer.cmd
```
* Create 3 new `server.properties` files. Pass each `server.properties` while starting the broker
	* Refer samples `server.properties` files - https://github.com/avinashbabudonthu/jms/tree/master/kafka/kafka-server-properies-files
* Start 3 brokers
```
.\bin\windows\kafka-server-start.bat .\config\server.properties
.\bin\windows\kafka-server-start.bat .\config\server-1.properties
.\bin\windows\kafka-server-start.bat .\config\server-2.properties
```
* Go to log files
	* we will have log folder with log files each with respective broker's logs
	
## How kafka cluster distributes the client requests
### How Topics are distributed across available brokers
* Let's say we have kafka clusted with 3 brokers
	* out of 3 brokers 1 broker will behave as controller. Normally this will be first broker which joined the cluster
* when create topic command is executed then zoo-keeper redirects request to controller
	* Let's say want to create `test` topic with 3 partitions
* Controller distributes the ownership of partitions to available brokers. This concept of distributing partitions to brokers is called `Leader Assignment`
* Finally `test` topic is distributed across kafka cluster
### Kafka distribute client requests from Kafka Producer
* Messages will go through `partitioner`
* Producer requests are distributed based on partitions. Since partitions present on different brokers, messages will go to different brokers
### Kafka distribute client requests from Kafka Consumer
* When poll is executed then request goes to all partitions and retrieve records from them
### Kafka distribute client requests with Kafka Consumer Groups
* Let's say we have 2 brokers, each with 1 partition
* We have 3 consumers with same group id. Now each consumer will be polling on each partition
* Now requests from consumer will be routed to respective partition and get message
### Summary
* Parition leader is assigned during topic creation
* Clients only invoke leader of partition  to produce and consume data
	* Load is evenly distributed between the brokers
	
## How kafka handles data loss when broker fails?
* We have kafka cluster with 3 brokers
	* broker-1
	* broker-2
	* broker-3
* We have 3 partitions distributed to 3 brokers
	* partition-0 to broker-1
	* partition-1 to broker-2
	* partition-2 to broker-3
* Now broker-1 goes down. This is leader or partition-0
	* All the data written to partition-0 present in broker-1 file system
	* Now data in broker-1 is lost
### Kafka handling data loss
* Kafka handles data loss issue told above using `Replication`
* We give `replication-factor` while creating topic
```
kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 3 --partitions 4 --topic topic-2
```
* Replication factor means number of copies of same message
* Kafka producer produced message to partition-0 and it goes to broker-1. Message is persisted to file system
* since `replication-factor=3`, 2 more copies of same message will be created to
	* broker-2
	* broker-3
* Here for this data
	* broker-1 is `Leader Replica`
	* broker-2, broker-3 are `Follower Replica`
	
## In-sync replica (ISR)
* Represents the number of replicas in sync with each other in the cluster including leder and follower replica
* Recommended value for in-sync-replica is greater than 1
* Ideal value is ISR == Replication Factor
* This can be configured with property `min.insync.replicas`
	* This can be set at `topic` or `broker` level
```
kafka-topics.bat --alter --zookeeper localhost:2181 --topic library-events --config min.insync.replicas=2
```	
## Fault Tolerance and Robustness
* Even though broker is down in cluster then clients does not know about it
* Client can still send and receive messages

## How to send message from spring boot to kafka
* Using `org.springframework.kafka.core.KafkaTemplate<K, V>`
### How KafkaTemplate works
* KafkaTemplate.send(): Sends message to kafka
* Before message goes to kafka it goes through some other layers as below
	* `Serializer`: Any record that sent to kafka needs to be serialized to `Bytes`. There are 2 types serialization techniques that applies to any record. This is mandatory for any producer. Client has to provide values for `key.serializer` and `value.serializer`. Kafka client java libraries comes with some pre-defined serializers
		* key.serializer
		* value.serializer
	* `Partitioner`: To decide which partition that message has to go in the topic. Kafka Producer API comes with `DefaultPartitioner` to handle partitioner logic. There are options to override `DefaultPartitioner` logic
	* `Record Accumulator`: Any record that sent from KafkaTemplate goes to Record Accumulator. Record Accumulator buffers the records and sent to kafka topic once buffer is full. This reason for this is to limit the number of calls from application to kafka cluster. Records in Record Accumulator are saved as `RecordBatch`.
		* Each RecordBatch has batch size. Represented by `batch.size` property. Value is number of bytes
		* Record Accumulator has buffer memory represented by property `buffer.memory`. Value is number of bytes
		* Once RecordBatch is full then message will be sent to Kafka topic
		* If batch is not full then Producer API is not going to wait until it is full. We can declare property `linger.ms` which is in milli seconds. If RecordBatch is not full but `linger.ms` time is up then message will be sent to kafka topic
### Configuring kafka template
* Mandatory values
```
bootstrap-servers: localhost:9092, localhost:9093, localhost:9094
key-serializer: org.apache.kafka.common.serialization.IntegerSerializer
value-serializer: org.apache.kafka.common.serialization.StringSerializer
```
* We can write custom key-Serializer or value-serializer
* KafkaTemplate Auto configuration with Spring Boot `application.yml`
```
spring:
	profiles: localhost
	kafka:
		producer:
			bootstrap-servers: localhost:9092, localhost:9093, localhost:9094
			key-serializer: org.apache.kafka.common.serialization.IntegerSerializer
			value-serializer: org.apache.kafka.common.serialization.StringSerializer
```
* `org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration` reads kafka properties from `application.yml` and create `KafkaTemplate`

## spring Kafka Consumers
* `MessageListenerContainer` interface. It has 2 implementations
	* KafkaMessageListenerContainer
	* ConcurrentMessageListenerContainer
* KafkaListener annotation
	* Uses `ConcurrentMessageListenerContainer` internally
### KafkaMessageListenerContainer
* Implementation of MessageListenerContainer
* Polls the records
* Commits the offsets
* Single threaded. Means poll call is handled by single thread
### ConcurrentMessageListenerContainer
* Represents multiple `KafkaMessageListenerContainer`
### KafkaListener annotation
* Simplest way to build kafka consumer
* Sample code
	* Add `@KafkaListener` annotation to a method in class which is bean
	* Add `@EnableKafka` annotation to Configuration class 
```
@KafkaListener
public void onMessage(ConsumerRecord<Integer, String> consumerRecord){
	log.info("consumer-record={}", consumerRecord);
}

@Configuration
@EnableKafka
@Slf4j
public class AppConfig{
	---
}
```
* We need to provide following KafkaConsumer configuration
	* key-deserializer
	* value-deserializer
	* group-id
* References - https://docs.spring.io/spring-kafka/reference/html/#receiving-messages

## Spring Boot Kafka Consumer auto configuration
* Every thing is from from `KafkaAutoConfiguration`, `KafkaAnnotationDrivenConfiguration` classes