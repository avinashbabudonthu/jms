# Kafka commands

* List Topics
```
kafka-topics.bat --list --zookeeper localhost:2181
```

* Create Topic
```
kafka-topics.bat --create --topic [topic-name] --replication-factor 1 --partitions 1 --zookeeper localhost:2181
```

* Delete Topic
```
kafka-run-class.bat --delete --topic [topic-name] kafka.admin.TopicCommand --zookeeper localhost:2181
```

* Describe Topic
```
kafka-topics.bat --describe --topic [Topic-Name] --zookeeper localhost:2181
```

* Kafka producer to send message
```
kafka-console-producer.bat --topic [topic-name] --broker-list localhost:9092
```

* Kafka consumer to receive message
```
kafka-console-consumer.bat --topic [topic-name] --zookeeper localhost:2181
```

* Read messages from beginning
```
kafka-console-consumer.bat --topic [Topic-Name] --from-beginning --zookeeper localhost:2181
```

* Start Zookeeper from cmd. Run this command from zookeeper/bin (For Ex: D:/Softwares/zookeeper-3.4.10/bin)
```
zkserver
```

