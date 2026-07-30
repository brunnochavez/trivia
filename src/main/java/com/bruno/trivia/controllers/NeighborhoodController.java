package com.bruno.trivia.controllers;
import com.bruno.trivia.dtos.NeighborhoodRequestDTO;
import com.bruno.trivia.dtos.NeighborhoodResponseDTO;
import com.bruno.trivia.services.NeighborhoodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping(path = "neighborhoods")
@RequiredArgsConstructor
public class NeighborhoodController {

    private final NeighborhoodService neighborhoodService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<NeighborhoodResponseDTO> findById(@PathVariable Long id){
        NeighborhoodResponseDTO neighborhoodResponseDTO = neighborhoodService.findById(id);
        return ResponseEntity.ok(neighborhoodResponseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<NeighborhoodResponseDTO>> findAll(@ParameterObject Pageable pageable){
        Page<NeighborhoodResponseDTO> pageNeighborhoods = neighborhoodService.findAll(pageable);
        return ResponseEntity.ok(pageNeighborhoods);
    }

    @PostMapping
    public ResponseEntity<NeighborhoodResponseDTO> insert(@Valid @RequestBody NeighborhoodRequestDTO requestDTO){
        NeighborhoodResponseDTO responseDTO = neighborhoodService.insert(requestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(responseDTO.id()).toUri();
        return ResponseEntity.created(uri).body(responseDTO);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<NeighborhoodResponseDTO> update(@PathVariable Long id, @Valid @RequestBody NeighborhoodRequestDTO requestDTO){
        NeighborhoodResponseDTO responseDTO = neighborhoodService.update(id, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        neighborhoodService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
