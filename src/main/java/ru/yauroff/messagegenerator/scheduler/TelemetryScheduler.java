package ru.yauroff.messagegenerator.scheduler;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.yauroff.messagegenerator.dto.TelemetryDTO;
import ru.yauroff.messagegenerator.repository.AgentDTORepository;
import ru.yauroff.messagegenerator.service.DataGeneratorService;
import ru.yauroff.messagegenerator.service.KafkaProducerService;

import java.util.stream.IntStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelemetryScheduler {
    @Value("${agent.message.number}")
    private Integer messNumbers;

    private final DataGeneratorService dataGeneratorService;

    private final AgentDTORepository agentDTORepository;

    private final KafkaProducerService kafkaProducerService;

    @Scheduled(fixedRate = 1000)
    public void generateTelemetry() {
        log.debug("GenerateTelemetry run");
        agentDTORepository.getAgents()
                          .stream()
                          .forEach(agent -> {
                              IntStream.range(0, messNumbers)
                                       .forEach(i -> {
                                           TelemetryDTO telemetryDTO = dataGeneratorService.generateTelemetry(agent.getAgent_id());
                                           log.debug("Generated telemetryDTO for send: {}", telemetryDTO);
                                           try {
                                               kafkaProducerService.sendTelemetry(telemetryDTO);
                                           } catch (JsonProcessingException e) {
                                               log.error("Error convert object to json: {}", e.getMessage());
                                           }
                                       });
                          });
    }
}
