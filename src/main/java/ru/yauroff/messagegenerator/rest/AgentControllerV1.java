package ru.yauroff.messagegenerator.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yauroff.messagegenerator.dto.AgentDTO;
import ru.yauroff.messagegenerator.service.AgentDTOService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/agents")
public class AgentControllerV1 {
    private final AgentDTOService agentDTOService;

    @GetMapping
    public ResponseEntity<List<AgentDTO>> getAllAgents() {
        List<AgentDTO> agents = agentDTOService.getAll();
        if (agents.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(agents, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AgentDTO> getUser(@PathVariable("id") String agentId) {
        if (agentId == null || agentId.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        AgentDTO agent = agentDTOService.getById(agentId);
        if (agent == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(agent, HttpStatus.OK);
    }
}
