package ru.yauroff.messagegenerator.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yauroff.messagegenerator.dto.AgentDTO;
import ru.yauroff.messagegenerator.repository.AgentDTORepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgentDTOServiceImplTest {
    @Mock
    private AgentDTORepository agentDTORepository;

    private AgentDTOServiceImpl instance;

    @BeforeEach
    public void before() {
        this.instance = new AgentDTOServiceImpl(agentDTORepository);
    }

    @Test
    void getAll() {
        AgentDTO agentDTO1 = new AgentDTO();
        agentDTO1.setAgent_id("1");
        AgentDTO agentDTO2 = new AgentDTO();
        agentDTO2.setAgent_id("2");

        when(this.agentDTORepository.getAgents()).thenReturn(List.of(agentDTO1, agentDTO2));
        assertEquals(this.instance.getAll(), List.of(agentDTO1, agentDTO2));
    }

    @Test
    void getById() {
        String id = "1";
        AgentDTO agentDTO = new AgentDTO();
        agentDTO.setAgent_id(id);

        when(this.agentDTORepository.getByAgentId(id)).thenReturn(agentDTO);
        assertEquals(this.instance.getById(id), agentDTO);
    }
}