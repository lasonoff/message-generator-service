package ru.yauroff.messagegenerator.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yauroff.messagegenerator.dto.AgentDTO;
import ru.yauroff.messagegenerator.dto.TelemetryDTO;
import ru.yauroff.messagegenerator.repository.AgentDTORepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DataGeneratorServiceImplTest {
    private DataGeneratorServiceImpl instance;

    @Mock
    private AgentDTORepository agentDTORepository;

    @BeforeEach
    public void before() {
        this.instance = new DataGeneratorServiceImpl(agentDTORepository);
    }

    @Test
    void createAllAgents() {
        instance.setAgentNumbers(10);
        instance.createAllAgents();
        ArgumentCaptor<AgentDTO> argumentAgentDTO = ArgumentCaptor.forClass(AgentDTO.class);
        verify(agentDTORepository, times(10)).create(argumentAgentDTO.capture());
    }

    @Test
    void generateAgent() {
        AgentDTO agentDTO = instance.generateAgent();
        Assertions.assertThat(DataGeneratorServiceImpl.MANUFACTURER)
                  .contains(agentDTO.getManufactured());
        Assertions.assertThat(DataGeneratorServiceImpl.OS)
                  .contains(agentDTO.getOs());
        assertNotNull(agentDTO.getAgent_id());
    }

    @Test
    void generateTelemetry() {
        String agentId = "123456";
        TelemetryDTO telemetryDTO = instance.generateTelemetry(agentId);
        assertNotNull(telemetryDTO.getUuid());
        assertEquals(telemetryDTO.getAgent_id(), agentId);
        Assertions.assertThat(DataGeneratorServiceImpl.SERVICE)
                  .contains(telemetryDTO.getActive_service());
    }
}