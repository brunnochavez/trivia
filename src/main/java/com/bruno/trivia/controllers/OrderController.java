package com.bruno.trivia.controllers;
import com.bruno.trivia.dtos.OrderRequestDTO;
import com.bruno.trivia.dtos.OrderResponseDTO;
import com.bruno.trivia.dtos.OrderStatusRequestDTO;
import com.bruno.trivia.services.OrderService;
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
@RequestMapping(path = "/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<OrderResponseDTO> findById(@PathVariable Long id){
        OrderResponseDTO responseDTO = orderService.findById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponseDTO>> findAll(@ParameterObject Pageable pageable){
        Page<OrderResponseDTO> responseDTOS = orderService.findAll(pageable);
        return ResponseEntity.ok(responseDTOS);
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> insert(@Valid @RequestBody OrderRequestDTO dto){
        OrderResponseDTO responseDTO = orderService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(responseDTO.id()).toUri();
        return ResponseEntity.created(uri).body(responseDTO);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<OrderResponseDTO> updateStatus(@PathVariable Long id,@Valid @RequestBody OrderStatusRequestDTO dto){
        OrderResponseDTO responseDTO = orderService.updateStatus(id, dto);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<OrderResponseDTO> cancel(@PathVariable Long id){
        OrderResponseDTO responseDTO = orderService.cancel(id);
        return ResponseEntity.ok(responseDTO);
    }
}
