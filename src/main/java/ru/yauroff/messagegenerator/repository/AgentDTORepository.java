package ru.yauroff.messagegenerator.repository;

import org.springframework.stereotype.Repository;
import ru.yauroff.messagegenerator.dto.AgentDTO;

import java.util.List;


public interface AgentDTORepository {

    void clear();

    AgentDTO getByAgentId(String agentId);

    List<AgentDTO> getAgents();

    String[] getAgentIds();

    AgentDTO create(AgentDTO agent);
}
