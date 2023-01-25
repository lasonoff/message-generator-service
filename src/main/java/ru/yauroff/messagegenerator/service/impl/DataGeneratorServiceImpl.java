package ru.yauroff.messagegenerator.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.yauroff.messagegenerator.dto.AgentDTO;
import ru.yauroff.messagegenerator.dto.TelemetryDTO;
import ru.yauroff.messagegenerator.repository.AgentDTORepository;
import ru.yauroff.messagegenerator.service.DataGeneratorService;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataGeneratorServiceImpl implements DataGeneratorService {
    static final String[] MANUFACTURER = {"Sony", "LG", "Apple", "Huawei", "Samsung", "Hp", "Lenovo"};
    static final String[] OS = {"IOS", "Windows10", "WindowsXP", "FreeDos", "VyOs", "Android"};
    static final String[] SERVICE = {"YOUTUBE", "Netflix", "Spotify", "Ruttor"};
    private final AgentDTORepository agentDTORepository;
    @Value("${agent.number}")
    private Integer agentNumbers;

    @Override
    @EventListener(ApplicationReadyEvent.class)
    public void createAllAgents() {
        IntStream.range(0, agentNumbers)
                 .forEach(i -> {
                     agentDTORepository.create(generateAgent());
                 });
        log.info("Generated {} agents", agentNumbers);
    }

    @Override
    public AgentDTO generateAgent() {
        return new AgentDTO(UUID.randomUUID()
                                .toString(),
                MANUFACTURER[new Random().nextInt(MANUFACTURER.length)],
                OS[new Random().nextInt(OS.length)]);
    }

    @Override
    public TelemetryDTO generateTelemetry(String agentId) {
        // now
        long rightLimit = Instant.now().getEpochSecond();
        // now -1 week
        long leftLimit = rightLimit - 7*24*60*60*1000L;
        return new TelemetryDTO(UUID.randomUUID()
                                    .toString(),
                agentId,
                //unix timestamp in the time range from now -1 week
                leftLimit + (long) (Math.random() * (rightLimit - leftLimit)),
                SERVICE[new Random().nextInt(SERVICE.length)],
                new Random().nextInt(100));
    }
}
