package ru.yauroff.messagegenerator.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import ru.yauroff.messagegenerator.dto.TelemetryDTO;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KafkaProducerServiceImplTest {
    private KafkaProducerServiceImpl instance;

    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @BeforeEach
    public void before() {
        this.instance = new KafkaProducerServiceImpl(objectMapper, kafkaTemplate);
    }

    @Test
    void sendTelemetry() throws JsonProcessingException, ExecutionException, InterruptedException {
        this.instance.setTopic("topic");
        TelemetryDTO telemetryDTO = new TelemetryDTO();
        when(objectMapper.writeValueAsString(telemetryDTO)).thenReturn(telemetryDTO.toString());
        when(kafkaTemplate.send("topic", telemetryDTO.getUuid(), telemetryDTO.toString())).thenReturn(null);

        CompletableFuture<TelemetryDTO> result = this.instance.sendTelemetry(telemetryDTO);
        ArgumentCaptor<String> argumentTopic = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> argumentIdDto = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> argumentDtoAsString = ArgumentCaptor.forClass(String.class);
        verify(this.kafkaTemplate).send(argumentTopic.capture(), argumentIdDto.capture(), argumentDtoAsString.capture());
        assertEquals(argumentDtoAsString.getValue(), telemetryDTO.toString());
        assertTrue(result.isDone());
        assertEquals(result.get(), telemetryDTO);
    }
}