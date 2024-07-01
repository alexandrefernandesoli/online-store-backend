package br.ufms.facom.onlinestorebackend.services;

import br.ufms.facom.onlinestorebackend.dtos.*;
import br.ufms.facom.onlinestorebackend.models.*;
import br.ufms.facom.onlinestorebackend.repositories.ClientRepository;
import br.ufms.facom.onlinestorebackend.repositories.OrderRepository;
import br.ufms.facom.onlinestorebackend.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, ClientRepository clientRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
    }

    @Transactional
    public OrderResponseDTO createOrderClient(OrderCreationClientDTO orderCreationDTO, Principal principal) {
        Client client = clientRepository.findByEmail(principal.getName());
        Order order = new Order();
        order.setClient(client);

        BigDecimal total = mapProductsToOrder(orderCreationDTO.getProducts(), order);

        order.setTotal(total);
        Order savedOrder = orderRepository.save(order);

        return convertToOrderResponseDTO(savedOrder);
    }

    @Transactional
    public OrderResponseDTO createOrder(OrderCreationDTO orderCreationDTO) {
        Order order = new Order();
        Optional<Client> client = clientRepository.findById(orderCreationDTO.getClientId());

        if (client.isEmpty()) {
            throw new ObjectNotFoundException(orderCreationDTO.getClientId(), "Client not found: " + orderCreationDTO.getClientId());
        }

        order.setClient(client.get());

        BigDecimal total = mapProductsToOrder(orderCreationDTO.getProducts(), order);

        order.setTotal(total);
        Order savedOrder = orderRepository.save(order);

        return convertToOrderResponseDTO(savedOrder);
    }

    private BigDecimal mapProductsToOrder(Set<OrderProductDTO> products, Order order) {
        Set<OrderProduct> orderProducts = new HashSet<>();
        for (OrderProductDTO prod : products) {
            Product product = productRepository.findById(prod.getProductId())
                    .orElseThrow(() -> new ObjectNotFoundException(prod.getProductId(), "Product not found: " + prod.getProductId()));

            BigDecimal price = product.getPrice(); // Default to regular price
            if (product.getSale() != null && product.getSale().compareTo(BigDecimal.ZERO) > 0 && product.getSale().compareTo(product.getPrice()) < 0) {
                price = product.getSale(); // Use sale price if it exists and is less than regular price
            }

            OrderProduct orderProduct = new OrderProduct(order, product, prod.getQuantity(), price);
            orderProducts.add(orderProduct);
        }
        order.setOrderProducts(orderProducts);

        return orderProducts.stream()
                .map(orderProduct -> orderProduct.getPrice().multiply(BigDecimal.valueOf(orderProduct.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<OrderResponseDTO> listOrdersByClient(Long clientId) {
        return orderRepository.findByClientId(clientId).stream()
                .map(this::convertToOrderResponseDTO)
                .collect(Collectors.toList());
    }

    public List<OrderResponseDTO> listOrders() {
        return orderRepository.findAll().stream()
                .map(this::convertToOrderResponseDTO)
                .collect(Collectors.toList());
    }

    private OrderResponseDTO convertToOrderResponseDTO(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setClient(new ClientResponseDTO(order.getClient()));
        dto.setCreatedAt(order.getCreatedAt());
        dto.setUpdatedAt(order.getUpdatedAt());
        dto.setTotal(order.getTotal());
        dto.setStatus(order.getStatus());

        Set<OrderProductResponseDTO> orderProductDTOs = order.getOrderProducts().stream()
                .map(this::convertToOrderProductResponseDTO)
                .collect(Collectors.toSet());
        dto.setProducts(orderProductDTOs);

        return dto;
    }

    private OrderProductResponseDTO convertToOrderProductResponseDTO(OrderProduct orderProduct) {
        OrderProductResponseDTO dto = new OrderProductResponseDTO();
        dto.setId(orderProduct.getProduct().getId());
        dto.setName(orderProduct.getProduct().getName());
        dto.setPrice(orderProduct.getPrice());
        dto.setQuantity(orderProduct.getQuantity());

        return dto;
    }

    public OrderResponseDTO getOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, "Order not found: " + id));

        return convertToOrderResponseDTO(order);
    }

    public void updateOrderStatus(Long id, Integer status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, "Order not found: " + id));

        OrderStatus newStatus = OrderStatus.fromValue(status);

        order.setStatus(newStatus);
        Order savedOrder = orderRepository.save(order);
    }
}
