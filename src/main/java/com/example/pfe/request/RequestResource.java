package com.example.pfe.request;

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
@RequestMapping(value = "/api/requests", produces = MediaType.APPLICATION_JSON_VALUE)
public class RequestResource {

    private final RequestService requestService;

    public RequestResource(final RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping
    public ResponseEntity<List<RequestDTO>> getAllRequests() {
        return ResponseEntity.ok(requestService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequestDTO> getRequest(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(requestService.get(id));
    }


    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createRequest(@RequestBody @Valid final RequestDTO requestDTO) {
        final Integer createdId = requestService.create(requestDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateRequest(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final RequestDTO requestDTO) {
        requestService.update(id, requestDTO);
        return ResponseEntity.ok(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateRequestview(@PathVariable(name = "id") final Integer id) {
        requestService.updateViewed(id);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRequest(@PathVariable(name = "id") final Integer id) {
        requestService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
