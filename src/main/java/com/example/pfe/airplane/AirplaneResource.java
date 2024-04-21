package com.example.pfe.airplane;

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
@RequestMapping(value = "/api/airplanes", produces = MediaType.APPLICATION_JSON_VALUE)
public class AirplaneResource {

    private final AirplaneService airplaneService;

    public AirplaneResource(final AirplaneService airplaneService) {
        this.airplaneService = airplaneService;
    }

    @GetMapping
    public ResponseEntity<List<AirplaneDTO>> getAllAirplanes() {
        return ResponseEntity.ok(airplaneService.findAll());
    }

    @GetMapping("/{idap}")
    public ResponseEntity<AirplaneDTO> getAirplane(
            @PathVariable(name = "idap") final Integer idap) {
        return ResponseEntity.ok(airplaneService.get(idap));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createAirplane(
            @RequestBody @Valid final AirplaneDTO airplaneDTO) {
        final Integer createdIdap = airplaneService.create(airplaneDTO);
        return new ResponseEntity<>(createdIdap, HttpStatus.CREATED);
    }

    @PutMapping("/{idap}")
    public ResponseEntity<Integer> updateAirplane(@PathVariable(name = "idap") final Integer idap,
            @RequestBody @Valid final AirplaneDTO airplaneDTO) {
        airplaneService.update(idap, airplaneDTO);
        return ResponseEntity.ok(idap);
    }

    @DeleteMapping("/{idap}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteAirplane(@PathVariable(name = "idap") final Integer idap) {
        final ReferencedWarning referencedWarning = airplaneService.getReferencedWarning(idap);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        airplaneService.delete(idap);
        return ResponseEntity.noContent().build();
    }

}
