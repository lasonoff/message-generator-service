package ru.yauroff.messagegenerator.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.yauroff.messagegenerator.dto.TelemetryDTO;
import ru.yauroff.messagegenerator.service.KafkaProducerService;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
@Data
public class KafkaProducerServiceImpl implements KafkaProducerService {
    private final ObjectMapper objectMapper;
    private final Producer<String, String> kafkaProducer;
    @Value("${kafka.topic}")
    private String topic;

    @Override
    @Async
    public CompletableFuture<TelemetryDTO> sendTelemetry(TelemetryDTO telemetryDTO) throws JsonProcessingException {
        String telemetryAsMessage = objectMapper.writeValueAsString(telemetryDTO);
        kafkaProducer.send(new ProducerRecord<>(topic, telemetryDTO.getUuid(), telemetryAsMessage));
        return CompletableFuture.completedFuture(telemetryDTO);
    }
}
