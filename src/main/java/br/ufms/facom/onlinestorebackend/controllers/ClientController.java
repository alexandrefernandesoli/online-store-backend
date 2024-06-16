package br.ufms.facom.onlinestorebackend.controllers;

import br.ufms.facom.onlinestorebackend.dtos.ClientRegistrationRequestDTO;
import br.ufms.facom.onlinestorebackend.services.ClientService;
import br.ufms.facom.onlinestorebackend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/public")
@RestController
public class ClientController {
    private final ClientService clientService;

    public ClientController(UserService userService, ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerClient(@RequestBody ClientRegistrationRequestDTO request) {
        clientService.registerClient(request);
        return ResponseEntity.ok("Client registered successfully");
    }
}