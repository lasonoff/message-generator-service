package ru.yauroff.messagegenerator.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yauroff.messagegenerator.dto.AgentDTO;
import ru.yauroff.messagegenerator.repository.AgentDTORepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class AgentDTORepositoryImpl implements AgentDTORepository {
    private final Map<String, AgentDTO> agents = new HashMap<>();

    @Override
    public void clear() {
        agents.clear();
    }

    @Override
    public AgentDTO getByAgentId(String agentId) {
        return agents.get(agentId);
    }

    @Override
    public List<AgentDTO> getAgents() {
        return agents.values()
                     .stream()
                     .collect(Collectors.toList());
    }

    @Override
    public String[] getAgentIds() {
        return agents.keySet()
                     .toArray(new String[0]);
    }

    @Override
    public AgentDTO create(AgentDTO agent) {
        agents.put(agent.getAgent_id(), agent);
        log.debug("Add agent to Repository:" + agent);
        return agent;
    }
}
