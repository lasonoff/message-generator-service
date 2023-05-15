package ru.yauroff.messagegenerator.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerConfig {
    @Value("${kafka.server}")
    private String kafkaServer;
    @Value("${kafka.topic}")
    private String kafkaTopic;

    @Value("${agent.number}")
    private Integer agentNumber;

    @Bean
    public Map<String, Object> producerConfigs() {
        log.info("Kafka server {}", kafkaServer);
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                kafkaServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);

        // TODO: убрать хардкод с временными настройками
        String USER = "kafka-user";
        String PASS = "kafka-user";
        String jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";
        String jaasCfg = String.format(jaasTemplate, USER, PASS);
        props.put("acks", "all");
        props.put("security.protocol", "SASL_PLAINTEXT");
        props.put("sasl.mechanism", "SCRAM-SHA-512");
        props.put("sasl.jaas.config", jaasCfg);
        return props;
    }

    /*@Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public NewTopic topic() {
        return TopicBuilder
                .name(kafkaTopic)
                .partitions(agentNumber)
                .replicas(1)
                .build();
    }*/
    @Bean
    public Producer<String, String> kafkaProducer() {
        return new KafkaProducer<>(producerConfigs());
    }
}
