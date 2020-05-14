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