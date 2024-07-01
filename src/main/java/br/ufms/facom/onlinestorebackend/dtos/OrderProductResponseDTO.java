package br.ufms.facom.onlinestorebackend.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderProductResponseDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private int quantity;
}
