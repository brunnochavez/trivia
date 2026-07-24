package com.bruno.trivia.dtos;
import com.bruno.trivia.entities.OrderType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequestDTO(

        @NotNull(message = "Necessário informar o método de pagamento")
        Long paymentMethodId,

        @NotNull(message = "Necessário informar o cliente")
        @Valid
        CustomerRequestDTO customer,

        @NotNull(message = "Tipo de venda obrigatório informar")
        OrderType orderType,

        @Valid
        DeliveryAddressRequestDTO delivery,

        @Valid
        @NotEmpty(message = "Insira pelo menos um produto")
        List<OrderItemRequestDTO> items
) {
}
