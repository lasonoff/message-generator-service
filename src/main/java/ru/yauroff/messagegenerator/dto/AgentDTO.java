package ru.yauroff.messagegenerator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AgentDTO {
    private String agent_id;
    private String manufactured;
    private String os;
}
