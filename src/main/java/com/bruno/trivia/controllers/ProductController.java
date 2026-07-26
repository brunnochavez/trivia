package com.bruno.trivia.controllers;
import com.bruno.trivia.dtos.ProductRequestDTO;
import com.bruno.trivia.dtos.ProductResponseDTO;
import com.bruno.trivia.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping(path = "/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id){
        ProductResponseDTO productResponseDTO = productService.findById(id);
        return ResponseEntity.ok(productResponseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> findAll(Pageable pageable){
        Page<ProductResponseDTO> pageProducts = productService.findAll(pageable);
        return ResponseEntity.ok(pageProducts);
    }

    @GetMapping(path = "/available")
    public ResponseEntity<Page<ProductResponseDTO>> findAvailable(Pageable pageable){
        Page<ProductResponseDTO> pageProducts = productService.findAvailable(pageable);
        return ResponseEntity.ok(pageProducts);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> insert(@Valid @RequestBody ProductRequestDTO requestDTO){
        ProductResponseDTO responseDTO = productService.insert(requestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(responseDTO.id()).toUri();
        return ResponseEntity.created(uri).body(responseDTO);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO requestDTO){
        ProductResponseDTO responseDTO = productService.update(id, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
