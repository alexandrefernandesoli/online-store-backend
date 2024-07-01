package br.ufms.facom.onlinestorebackend.controllers;

import br.ufms.facom.onlinestorebackend.dtos.OrderCreationClientDTO;
import br.ufms.facom.onlinestorebackend.dtos.OrderResponseDTO;
import br.ufms.facom.onlinestorebackend.models.Client;
import br.ufms.facom.onlinestorebackend.repositories.ClientRepository;
import br.ufms.facom.onlinestorebackend.services.ClientService;
import br.ufms.facom.onlinestorebackend.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/client/orders")
public class ClientOrderController {
    private final OrderService orderService;
    private final ClientService clientService;

    public ClientOrderController(OrderService orderService, ClientService clientService) {
        this.orderService = orderService;
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getOrders(Principal principal) {
        String clientEmail = principal.getName();

        Optional<Client> client = clientService.getClientByEmail(clientEmail);

        if (client.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<OrderResponseDTO> orders = orderService.listOrdersByClient(client.get().getId());

        return ResponseEntity.ok(orders);
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderCreationClientDTO orderCreationClientDTO, Principal principal) {
        OrderResponseDTO order = orderService.createOrderClient(orderCreationClientDTO, principal);

        return ResponseEntity.ok(order);
    }
}
