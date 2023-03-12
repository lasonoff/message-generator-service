package ru.yauroff.messagegenerator.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.yauroff.messagegenerator.dto.TelemetryDTO;
import ru.yauroff.messagegenerator.service.KafkaProducerService;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducerServiceImpl implements KafkaProducerService {
    @Value("${kafka.topic}")
    private String topic;

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    @Async
    public CompletableFuture<TelemetryDTO> sendTelemetry(TelemetryDTO telemetryDTO) throws JsonProcessingException {
        String telemetryAsMessage = objectMapper.writeValueAsString(telemetryDTO);
        kafkaTemplate.send(topic, telemetryDTO.getUuid(), telemetryAsMessage);
        return CompletableFuture.completedFuture(telemetryDTO);
    }
}
