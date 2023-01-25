package ru.yauroff.messagegenerator.service;

import ru.yauroff.messagegenerator.dto.AgentDTO;

import java.util.List;

public interface AgentDTOService {
    List<AgentDTO> getAll();

    AgentDTO getById(String agentId);
}
