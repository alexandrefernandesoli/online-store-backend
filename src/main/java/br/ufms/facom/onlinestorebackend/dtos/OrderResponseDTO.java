package br.ufms.facom.onlinestorebackend.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class OrderResponseDTO {
    private Long id;
    private String clientEmail;
    private Set<OrderProductResponseDTO> products;
    private BigDecimal total;
    private Date createdAt;
    private Date updatedAt;
}

