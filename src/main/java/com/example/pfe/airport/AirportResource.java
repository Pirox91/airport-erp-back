package com.example.pfe.airport;

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
@RequestMapping(value = "/api/airports", produces = MediaType.APPLICATION_JSON_VALUE)
public class AirportResource {

    private final AirportService airportService;

    public AirportResource(final AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping
    public ResponseEntity<List<AirportDTO>> getAllAirports() {
        return ResponseEntity.ok(airportService.findAll());
    }

    @GetMapping("/{idarpt}")
    public ResponseEntity<AirportDTO> getAirport(
            @PathVariable(name = "idarpt") final Integer idarpt) {
        return ResponseEntity.ok(airportService.get(idarpt));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createAirport(@RequestBody @Valid final AirportDTO airportDTO) {
        final Integer createdIdarpt = airportService.create(airportDTO);
        return new ResponseEntity<>(createdIdarpt, HttpStatus.CREATED);
    }

    @PutMapping("/{idarpt}")
    public ResponseEntity<Integer> updateAirport(
            @PathVariable(name = "idarpt") final Integer idarpt,
            @RequestBody @Valid final AirportDTO airportDTO) {
        airportService.update(idarpt, airportDTO);
        return ResponseEntity.ok(idarpt);
    }

    @DeleteMapping("/{idarpt}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteAirport(@PathVariable(name = "idarpt") final Integer idarpt) {
        final ReferencedWarning referencedWarning = airportService.getReferencedWarning(idarpt);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        airportService.delete(idarpt);
        return ResponseEntity.noContent().build();
    }

}
