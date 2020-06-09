package com.app.config;

import org.apache.avro.Schema;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import io.confluent.kafka.serializers.AbstractKafkaAvroDeserializer;

public class CustomKafkaAvroDeserializer extends AbstractKafkaAvroDeserializer implements Deserializer<Object> {

	@Override
	protected Object deserialize(byte[] payload) throws SerializationException {
		try {
			return super.deserialize(payload);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Object deserialize(byte[] payload, Schema readerSchema) throws SerializationException {
		try {
			return super.deserialize(payload, readerSchema);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Object deserialize(String topic, byte[] data) {
		try {
			String dataString = new String(data);
			return deserialize(data);
		} catch (SerializationException e) {
			return null;
		}
	}
}