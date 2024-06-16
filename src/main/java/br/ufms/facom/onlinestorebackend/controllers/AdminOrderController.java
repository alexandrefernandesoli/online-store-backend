package br.ufms.facom.onlinestorebackend.controllers;

import br.ufms.facom.onlinestorebackend.dtos.OrderCreationDTO;
import br.ufms.facom.onlinestorebackend.dtos.OrderResponseDTO;
import br.ufms.facom.onlinestorebackend.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/orders")
public class AdminOrderController {
    private final OrderService orderService;

    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO > createOrder(@Valid @RequestBody OrderCreationDTO orderCreationDTO) {
        OrderResponseDTO order = orderService.createOrder(orderCreationDTO);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> listOrders() {
        List<OrderResponseDTO> orders = orderService.listOrders();
        return ResponseEntity.ok(orders);
    }
}

