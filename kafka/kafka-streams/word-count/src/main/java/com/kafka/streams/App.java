package com.kafka.streams;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.processor.TopologyBuilder;

import java.util.Arrays;
import java.util.Properties;

@Slf4j
public class App {

    public static void main(String[] args) {
        log.info("Set Properties");
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafka-stream-word-count-app");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        // 1 - stream from Kafka
        KStreamBuilder kStreamBuilder = new KStreamBuilder();
        KStream<String, String> wordCountInput = kStreamBuilder.stream("word-count-input");

        KTable<String, Long> wordCount = wordCountInput
        .mapValues(String::toLowerCase) // 2 - map values to lower case
        .flatMapValues(value -> Arrays.asList(value.split(" "))) // 3 - flatMap values split by space
        .selectKey((key, value) -> value) // 4 - select key to apply key
        .groupByKey() // 5 - group by key before aggregation
        .count("counts") // 6 - count occurences
        ;

        // 7 write results back to kafka
        wordCount.to(Serdes.String(), Serdes.Long(), "word-count-output");

        KafkaStreams streams = new KafkaStreams(kStreamBuilder, properties);
        streams.start();

        // print the topology
        log.info("{}", streams.toString());

        // shutdown hook to correctly close the streams application
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}