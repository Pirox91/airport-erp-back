package com.example.pfe.assigned;

import com.example.pfe.controllers.notification.NotificationService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
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
    private NotificationService notificationService;

    public AssignedResource(final AssignedService assignedService) {
        this.assignedService = assignedService;
    }

    @GetMapping
    public ResponseEntity<List<AssignedDTO>> getAllAssigneds() {
        return ResponseEntity.ok(assignedService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssignedDTO> getAssigned(@PathVariable(name = "id") final Integer id) {
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

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createAssigned(
            @RequestBody @Valid final AssignedDTO assignedDTO) {
        final Integer createdId = assignedService.create(assignedDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateAssigned(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final AssignedDTO assignedDTO) {
        assignedService.update(id, assignedDTO);
        notificationService.sendNotification(assignedDTO);

        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteAssigned(@PathVariable(name = "id") final Integer id) {
        assignedService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
