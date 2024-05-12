package com.example.pfe.flight_schedule;

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
@RequestMapping(value = "/api/flightSchedules", produces = MediaType.APPLICATION_JSON_VALUE)
public class FlightScheduleResource {

    private final FlightScheduleService flightScheduleService;

    public FlightScheduleResource(final FlightScheduleService flightScheduleService) {
        this.flightScheduleService = flightScheduleService;
    }

    @GetMapping
    public ResponseEntity<List<YetAnotherFsDTO>> getAllFlightSchedules() {
        return ResponseEntity.ok(flightScheduleService.findAll());
    }

    @GetMapping("/{idfs}")
    public ResponseEntity<YetAnotherFsDTO> getFlightSchedule(
            @PathVariable(name = "idfs") final Integer idfs) {
        return ResponseEntity.ok(flightScheduleService.get(idfs));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createFlightSchedule(
            @RequestBody @Valid final FlightScheduleDTO flightScheduleDTO) {
        final Integer createdIdfs = flightScheduleService.create(flightScheduleDTO);
        return new ResponseEntity<>(createdIdfs, HttpStatus.CREATED);
    }

    @PutMapping("/{idfs}")
    public ResponseEntity<Integer> updateFlightSchedule(
            @PathVariable(name = "idfs") final Integer idfs,
            @RequestBody @Valid final FlightScheduleDTO flightScheduleDTO) {
        flightScheduleService.update(idfs, flightScheduleDTO);
        return ResponseEntity.ok(idfs);
    }

    @DeleteMapping("/{idfs}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteFlightSchedule(
            @PathVariable(name = "idfs") final Integer idfs) {
        final ReferencedWarning referencedWarning = flightScheduleService.getReferencedWarning(idfs);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        flightScheduleService.delete(idfs);
        return ResponseEntity.noContent().build();
    }

}
