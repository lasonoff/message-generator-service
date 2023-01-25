package ru.yauroff.messagegenerator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.yauroff.messagegenerator.dto.TelemetryDTO;

import java.util.concurrent.CompletableFuture;

public interface KafkaProducerService {
    CompletableFuture<TelemetryDTO> sendTelemetry(TelemetryDTO telemetryDTO) throws JsonProcessingException;
}
