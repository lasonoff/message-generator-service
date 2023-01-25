package ru.yauroff.messagegenerator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TelemetryDTO {
    private String uuid;
    private String agent_id;
    private Long previous_message_time;
    private String active_service;
    private Integer quality_score;
}
