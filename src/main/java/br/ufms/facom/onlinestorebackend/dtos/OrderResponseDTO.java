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
    private ClientResponseDTO client;
    private Set<OrderProductResponseDTO> products;
    private BigDecimal total;
    private Integer status;
    private Date createdAt;
    private Date updatedAt;
}

