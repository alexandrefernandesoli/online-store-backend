package br.ufms.facom.onlinestorebackend.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class OrderCreationClientDTO {

    @NotEmpty(message = "Order must contain at least one product")
    @Valid
    private Set<OrderProductDTO> products;
}
