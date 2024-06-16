package br.ufms.facom.onlinestorebackend.services;

import br.ufms.facom.onlinestorebackend.dtos.ClientRegistrationRequestDTO;
import br.ufms.facom.onlinestorebackend.models.Client;
import br.ufms.facom.onlinestorebackend.repositories.ClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public ClientService(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerClient(ClientRegistrationRequestDTO request) {
        if (clientRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        Client client = new Client();
        client.setFirstName(request.getFirstName());
        client.setLastName(request.getLastName());
        client.setEmail(request.getEmail());
        client.setPassword(passwordEncoder.encode(request.getPassword()));

        clientRepository.save(client);
    }
}
