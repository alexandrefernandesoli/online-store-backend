package br.ufms.facom.onlinestorebackend.services;

import br.ufms.facom.onlinestorebackend.dtos.OrderCreationDTO;
import br.ufms.facom.onlinestorebackend.dtos.OrderProductDTO;
import br.ufms.facom.onlinestorebackend.dtos.OrderProductResponseDTO;
import br.ufms.facom.onlinestorebackend.dtos.OrderResponseDTO;
import br.ufms.facom.onlinestorebackend.models.Order;
import br.ufms.facom.onlinestorebackend.models.OrderProduct;
import br.ufms.facom.onlinestorebackend.models.Product;
import br.ufms.facom.onlinestorebackend.repositories.OrderRepository;
import br.ufms.facom.onlinestorebackend.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public OrderResponseDTO createOrder(OrderCreationDTO orderCreationDTO) {
        Order order = new Order();
        order.setClientEmail(orderCreationDTO.getClientEmail());

        BigDecimal total = BigDecimal.ZERO; // Initialize total price to zero

        Set<OrderProduct> orderProducts = new HashSet<>();
        for (OrderProductDTO prod : orderCreationDTO.getProducts()) {
            Product product = productRepository.findById(prod.getProductId())
                    .orElseThrow(() -> new ObjectNotFoundException(prod.getProductId(), "Product not found: " + prod.getProductId()));

            BigDecimal price = product.getPrice(); // Default to regular price
            if (product.getSale() != null && product.getSale().compareTo(BigDecimal.ZERO) > 0 && product.getSale().compareTo(product.getPrice()) < 0) {
                price = product.getSale(); // Use sale price if it exists and is less than regular price
            }

            BigDecimal productTotal = price.multiply(BigDecimal.valueOf(prod.getQuantity())); // Calculate total for this product
            total = total.add(productTotal); // Add product total to order total

            OrderProduct orderProduct = new OrderProduct(order, product, prod.getQuantity());
            orderProducts.add(orderProduct);
        }

        order.setOrderProducts(orderProducts);
        order.setTotal(total); // Set total price for the order
        Order savedOrder = orderRepository.save(order);

        return convertToOrderResponseDTO(savedOrder);
    }

    public List<OrderResponseDTO> listOrders() {
        return orderRepository.findAll().stream()
                .map(this::convertToOrderResponseDTO)
                .collect(Collectors.toList());
    }

    private OrderResponseDTO convertToOrderResponseDTO(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setClientEmail(order.getClientEmail());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setUpdatedAt(order.getUpdatedAt());
        dto.setTotal(order.getTotal());

        Set<OrderProductResponseDTO> orderProductDTOs = order.getOrderProducts().stream()
                .map(this::convertToOrderProductResponseDTO)
                .collect(Collectors.toSet());
        dto.setProducts(orderProductDTOs);

        return dto;
    }

    private OrderProductResponseDTO convertToOrderProductResponseDTO(OrderProduct orderProduct) {
        OrderProductResponseDTO dto = new OrderProductResponseDTO();
        dto.setId(orderProduct.getProduct().getId());
        dto.setName(orderProduct.getProduct().getName()); // Assuming Product has a name field
        dto.setSale(orderProduct.getProduct().getSale());
        dto.setPrice(orderProduct.getProduct().getPrice());
        dto.setQuantity(orderProduct.getQuantity());

        return dto;
    }
}
