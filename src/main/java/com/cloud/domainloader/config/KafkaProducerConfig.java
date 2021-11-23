package com.cloud.domainloader.config;

import com.cloud.domainloader.model.WebDomain;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    private final static String BOOTSTRAP_SERVERS_CONFIG= "127.0.0.1:9092";

    @Bean
    public KafkaTemplate<String, WebDomain> kafkaTemplate() {

        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ProducerFactory<String, WebDomain> producerFactory() {

        Map<String, Object> pfConfig= new HashMap<>();
        pfConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_CONFIG);
        pfConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        pfConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(pfConfig);
    }
}
