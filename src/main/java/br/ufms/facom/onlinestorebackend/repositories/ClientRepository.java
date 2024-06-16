package br.ufms.facom.onlinestorebackend.repositories;

import br.ufms.facom.onlinestorebackend.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByEmail(String email);
    boolean existsByEmail(String email);
}
