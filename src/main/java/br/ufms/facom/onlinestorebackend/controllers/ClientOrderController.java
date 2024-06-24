package br.ufms.facom.onlinestorebackend.controllers;

import br.ufms.facom.onlinestorebackend.dtos.OrderCreationClientDTO;
import br.ufms.facom.onlinestorebackend.dtos.OrderResponseDTO;
import br.ufms.facom.onlinestorebackend.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/client/orders")
public class ClientOrderController {
    private final OrderService orderService;

    public ClientOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getOrders(Principal principal) {
        String clientEmail = principal.getName();
        List<OrderResponseDTO> orders = orderService.listOrdersByClientEmail(clientEmail);

        return ResponseEntity.ok(orders);
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderCreationClientDTO orderCreationClientDTO, Principal principal) {
        OrderResponseDTO order = orderService.createOrderClient(orderCreationClientDTO, principal);

        return ResponseEntity.ok(order);
    }
}
