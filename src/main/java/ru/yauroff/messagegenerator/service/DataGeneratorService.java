package ru.yauroff.messagegenerator.service;

import ru.yauroff.messagegenerator.dto.AgentDTO;
import ru.yauroff.messagegenerator.dto.TelemetryDTO;

public interface DataGeneratorService {
    void createAllAgents();

    AgentDTO generateAgent();

    TelemetryDTO generateTelemetry(String agentId);
}
