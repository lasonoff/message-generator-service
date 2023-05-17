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
    private final String JAAS_TEMPLATE = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";
    @Value("${kafka.server}")
    private String kafkaServer;
    @Value("${kafka.user}")
    private String kafkaUser;
    @Value("${kafka.password}")
    private String kafkaPassword;
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

        if (kafkaUser != null && !kafkaUser.isEmpty()) {
            String jaasCfg = String.format(JAAS_TEMPLATE, kafkaUser, kafkaPassword);
            props.put(ProducerConfig.ACKS_CONFIG, "all");
            props.put("security.protocol", "SASL_PLAINTEXT");
            props.put("sasl.mechanism", "SCRAM-SHA-512");
            props.put("sasl.jaas.config", jaasCfg);
        }
        return props;
    }

    @Bean
    public Producer<String, String> kafkaProducer() {
        return new KafkaProducer<>(producerConfigs());
    }
}
