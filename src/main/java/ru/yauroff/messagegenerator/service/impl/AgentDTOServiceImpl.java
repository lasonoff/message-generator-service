package ru.yauroff.messagegenerator.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yauroff.messagegenerator.dto.AgentDTO;
import ru.yauroff.messagegenerator.repository.AgentDTORepository;
import ru.yauroff.messagegenerator.service.AgentDTOService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgentDTOServiceImpl implements AgentDTOService {
    private final AgentDTORepository agentDTORepository;

    @Override
    public List<AgentDTO> getAll() {
        return agentDTORepository.getAgents();
    }

    @Override
    public AgentDTO getById(String agentId) {
        return agentDTORepository.getByAgentId(agentId);
    }
}
