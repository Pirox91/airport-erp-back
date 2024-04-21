package com.example.pfe.weekly;

import com.example.pfe.util.ReferencedException;
import com.example.pfe.util.ReferencedWarning;
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
@RequestMapping(value = "/api/weeklies", produces = MediaType.APPLICATION_JSON_VALUE)
public class WeeklyResource {

    private final WeeklyService weeklyService;

    public WeeklyResource(final WeeklyService weeklyService) {
        this.weeklyService = weeklyService;
    }

    @GetMapping
    public ResponseEntity<List<WeeklyDTO>> getAllWeeklies() {
        return ResponseEntity.ok(weeklyService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WeeklyDTO> getWeekly(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(weeklyService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createWeekly(@RequestBody @Valid final WeeklyDTO weeklyDTO) {
        final Integer createdId = weeklyService.create(weeklyDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateWeekly(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final WeeklyDTO weeklyDTO) {
        weeklyService.update(id, weeklyDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteWeekly(@PathVariable(name = "id") final Integer id) {
        final ReferencedWarning referencedWarning = weeklyService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        weeklyService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
