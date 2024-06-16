package br.ufms.facom.onlinestorebackend.repositories;
import br.ufms.facom.onlinestorebackend.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}