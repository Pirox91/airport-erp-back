package com.example.pfe.path;

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
@RequestMapping(value = "/api/paths", produces = MediaType.APPLICATION_JSON_VALUE)
public class PathResource {

    private final PathService pathService;

    public PathResource(final PathService pathService) {
        this.pathService = pathService;
    }

    @GetMapping
    public ResponseEntity<List<PathDTO>> getAllPaths() {
        return ResponseEntity.ok(pathService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PathDTO> getPath(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(pathService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createPath(@RequestBody @Valid final PathDTO pathDTO) {
        final Integer createdId = pathService.create(pathDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updatePath(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final PathDTO pathDTO) {
        pathService.update(id, pathDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePath(@PathVariable(name = "id") final Integer id) {
        pathService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
