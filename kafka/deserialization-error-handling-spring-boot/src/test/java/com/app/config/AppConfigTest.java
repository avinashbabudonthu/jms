package com.app.config;

import org.apache.kafka.clients.consumer.Consumer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

import com.app.avro.model.User;

@RunWith(MockitoJUnitRunner.class)
public class AppConfigTest {

	private AppConfig appConfig = new AppConfig();

	@Test
	public void kafkaListenerContainerFactory() {
		@SuppressWarnings("unchecked")
		ConsumerFactory<String, User> consumerFactoryMock = Mockito.mock(ConsumerFactory.class);
		System.out.println(consumerFactoryMock);

		ConcurrentKafkaListenerContainerFactory<String, User> factory = appConfig
				.kafkaListenerContainerFactory(consumerFactoryMock);
		Assert.assertNotNull(factory);
	}

	@Test
	public void handleError() {
		Consumer<?, ?> consumer = Mockito.mock(Consumer.class);
		Exception e = new Exception(
				"Test Error deserializing key/value for partition practice-topic-2-0 at offset 8. If needed, please seek past the record to continue consumption.");
		appConfig.handleError(e, consumer);
	}

}
