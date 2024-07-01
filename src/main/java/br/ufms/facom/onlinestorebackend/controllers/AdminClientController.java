package br.ufms.facom.onlinestorebackend.controllers;

import br.ufms.facom.onlinestorebackend.dtos.ClientResponseDTO;
import br.ufms.facom.onlinestorebackend.models.Client;
import br.ufms.facom.onlinestorebackend.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/clients")
public class AdminClientController {
    private final ClientService clientService;

    public AdminClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> getClients() {
        List<Client> clientList = clientService.getClients();

        return ResponseEntity.ok(clientList.stream().map(ClientResponseDTO::new).toList());
    }
}
