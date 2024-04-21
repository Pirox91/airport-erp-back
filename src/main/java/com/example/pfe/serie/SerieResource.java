package com.example.pfe.serie;

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
@RequestMapping(value = "/api/series", produces = MediaType.APPLICATION_JSON_VALUE)
public class SerieResource {

    private final SerieService serieService;

    public SerieResource(final SerieService serieService) {
        this.serieService = serieService;
    }

    @GetMapping
    public ResponseEntity<List<SerieDTO>> getAllSeries() {
        return ResponseEntity.ok(serieService.findAll());
    }

    @GetMapping("/{ids}")
    public ResponseEntity<SerieDTO> getSerie(@PathVariable(name = "ids") final Integer ids) {
        return ResponseEntity.ok(serieService.get(ids));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createSerie(@RequestBody @Valid final SerieDTO serieDTO) {
        final Integer createdIds = serieService.create(serieDTO);
        return new ResponseEntity<>(createdIds, HttpStatus.CREATED);
    }

    @PutMapping("/{ids}")
    public ResponseEntity<Integer> updateSerie(@PathVariable(name = "ids") final Integer ids,
            @RequestBody @Valid final SerieDTO serieDTO) {
        serieService.update(ids, serieDTO);
        return ResponseEntity.ok(ids);
    }

    @DeleteMapping("/{ids}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSerie(@PathVariable(name = "ids") final Integer ids) {
        final ReferencedWarning referencedWarning = serieService.getReferencedWarning(ids);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        serieService.delete(ids);
        return ResponseEntity.noContent().build();
    }

}
