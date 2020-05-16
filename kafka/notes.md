# Kafka Notes

## Basics
* Message retaining
	* In traditional messaging system has transient message persistance. Means once message is consumed by consumer it will be deleted from the message broker
	* In the case of Kafka event will be saved in the file system where kafka is installed. Events are retained for certain time
* All the events in kafka are immutable. Means once message sent to kafka then it cannot be altered
* Any consumer we have access to broker can read the message
* Kafka is distributed streaming system
* Kafka is streaming system. Not a just messaging system

## Kafka Terminology and Client APIs
* Kafka Cluster: consists of multiple brokers
* In order to manage multiple brokers we need Zookeeper
	* Zookeeper keeps track of health of brokers and manage the cluster for you
* Broker is what all kafka clients interact with
* Kafka Producer
	* client to kafka broker
	* produce new data to kafka
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
* Kafka topic is entity like table in database
* Topic live inside kafka broker
* Kafka clients uses topic name to produce and consume messages
* kafka consumers generally poll continuously for new messsages
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
* File system where that records needs to be written is configured property `log.dirs` property in `kafka-directory/config/server.properties` file
* File will be present in that location with extension `.log`
* Each partition will have it's own log
* After messages are written to log files then records are committed. Consumer who is polling records can only see records which are committed to the file system
* As new records produced they get appended to log file
### Retention Policy
* Determines how long the message is going to be retained?
* Retention policy is configured using property `log.retention.hours` in `kafka-directory/config/server.properties` file. Default retention period is `168 hours` that is `7 days`

## Kafka as distributed streaming platform
### What is distributed system
* collection of system work together to deliver the functionality
#### Charateristics of distributed system
* Availability and fault tolerance
* Reliable work distribution. client requests equally distributed among the available systems
* Easily scalable
* Handling concurrency is easy
### Single point of failure
* All Producer and consumers interact with kafks with Broker
* If broker goes down then all producer and consumer requests will fail
* This will become single point of failure
### How to solve single point of failure
* Let's say we have cluster of 3 brokers
*  cluster will be managed by zoo-keeper
* All brokers send heart beat to zoo-keeper at regular intervals to ensure state of kafka broker is healthy to serve client requests
* Client requests distributed among 3 brokers. If any of the broker goes down then zoo-keeper gets notified then all client requests will be routed to other available brokers

## Setting up kafka cluster in local with 3 brokers
* In order to start multiple kafk brokers, we need to have new `server.properties` file with new broker details. This new `server.properties` should have unique values compared to other `server.properties`
```
broker.id=1
listeners=PLAINTEXT://localhost:9093
log.dirs=C:\softwares\kafka\logs2
auto.create.topics.enable=false(optional)
```
* Start `zoo-keeper`
```
\zoo-keeper\apache-zookeeper-3.6.1-bin.tar\apache-zookeeper-3.6.1-bin\apache-zookeeper-3.6.1-bin\bin\zkServer.cmd
```
* Create new `server.properties` files. Pass each `server.properties` while starting the broker