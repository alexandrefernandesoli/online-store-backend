package br.ufms.facom.onlinestorebackend.repositories;
import br.ufms.facom.onlinestorebackend.models.Client;
import br.ufms.facom.onlinestorebackend.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByClientId(Long clientId);
}