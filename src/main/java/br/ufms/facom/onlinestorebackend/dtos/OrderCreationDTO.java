package br.ufms.facom.onlinestorebackend.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class OrderCreationDTO {
    @NotNull(message = "Client cannot be null")
    private Long clientId;

    @NotEmpty(message = "Order must contain at least one product")
    @Valid
    private Set<OrderProductDTO> products;
}
