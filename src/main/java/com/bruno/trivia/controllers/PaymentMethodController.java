package com.bruno.trivia.controllers;
import com.bruno.trivia.dtos.PaymentMethodRequestDTO;
import com.bruno.trivia.dtos.PaymentMethodResponseDTO;
import com.bruno.trivia.services.PaymentMethodService;
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
@RequestMapping(path = "/payment-methods")
@RequiredArgsConstructor
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<PaymentMethodResponseDTO> findById(@PathVariable Long id){
        PaymentMethodResponseDTO paymentMethodResponseDTO = paymentMethodService.findById(id);
        return ResponseEntity.ok(paymentMethodResponseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<PaymentMethodResponseDTO>> findAll(@ParameterObject Pageable pageable){
        Page<PaymentMethodResponseDTO> pagePaymentMethods = paymentMethodService.findAll(pageable);
        return ResponseEntity.ok(pagePaymentMethods);
    }

    @PostMapping
    public ResponseEntity<PaymentMethodResponseDTO> insert(@Valid @RequestBody PaymentMethodRequestDTO requestDTO){
        PaymentMethodResponseDTO responseDTO = paymentMethodService.insert(requestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(responseDTO.id()).toUri();
        return ResponseEntity.created(uri).body(responseDTO);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<PaymentMethodResponseDTO> update(@PathVariable Long id, @Valid @RequestBody PaymentMethodRequestDTO requestDTO){
        PaymentMethodResponseDTO responseDTO = paymentMethodService.update(id, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        paymentMethodService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
