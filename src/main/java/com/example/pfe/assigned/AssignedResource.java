package com.example.pfe.assigned;

import com.example.pfe.config.MyWebSocketHandler;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/assigneds", produces = MediaType.APPLICATION_JSON_VALUE)
public class AssignedResource {

    private final AssignedService assignedService;
    private final MyWebSocketHandler webSocketHandler;
    public AssignedResource(final AssignedService assignedService,MyWebSocketHandler webSocketHandler) {
        this.assignedService = assignedService;
        this.webSocketHandler = webSocketHandler;
    }

    @GetMapping
    public ResponseEntity<List<YetAnotherAssignedDTO>> getAllAssigneds() {

        return ResponseEntity.ok(assignedService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<YetAnotherAssignedDTO> getAssigned(@PathVariable(name = "id") final Integer id) {

        return ResponseEntity.ok(assignedService.get(id));
    }
    @GetMapping("fs/{idfs}")
    public ResponseEntity<YetAnotherAssignedDTO> getAssignedbyFs(@PathVariable(name = "idfs") final Integer idfs) {
        return ResponseEntity.ok(assignedService.getbyfs(idfs));
    }
    @GetMapping("/assignments/{userId}")
    public ResponseEntity<List<YetAnotherAssignedDTO>> getAssignmentsByUserId(@PathVariable Long userId) {
        List<YetAnotherAssignedDTO> assignments = assignedService.getAssignmentsByUser(userId);
        return ResponseEntity.ok(assignments);
    }
    @GetMapping("/available/{t1}/{t2}")
    public ResponseEntity<List<UserDTO>>getAvailableAssignedUsers(@PathVariable LocalDateTime t1, @PathVariable LocalDateTime t2) {
        List<UserDTO> users= assignedService.findAvailable(t1,t2);
        return ResponseEntity.ok(users);
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createAssigned(
            @RequestBody @Valid final AssignedDTO assignedDTO) {
        final Integer createdId = assignedService.create(assignedDTO);
        webSocketHandler.sendMessageToAll("updatedAssignedID "+createdId.toString());

        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateAssigned(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final AssignedDTO assignedDTO) {
        webSocketHandler.sendMessageToAll("updatedAssignedID "+id.toString());

        assignedService.update(id, assignedDTO);

        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteAssigned(@PathVariable(name = "id") final Integer id) {
        webSocketHandler.sendMessageToAll("deleteddAssignedID "+id.toString());

        assignedService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
