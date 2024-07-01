package br.ufms.facom.onlinestorebackend.controllers;

import br.ufms.facom.onlinestorebackend.dtos.OrderCreationDTO;
import br.ufms.facom.onlinestorebackend.dtos.OrderResponseDTO;
import br.ufms.facom.onlinestorebackend.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable Long id) {
        OrderResponseDTO order = orderService.getOrder(id);
        return ResponseEntity.ok(order);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long id, @RequestParam Integer status) {
        orderService.updateOrderStatus(id, status);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Order status updated successfully");

        return ResponseEntity.ok(response);
    }
}

