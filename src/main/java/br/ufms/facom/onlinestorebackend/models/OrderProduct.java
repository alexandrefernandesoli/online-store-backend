package br.ufms.facom.onlinestorebackend.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "order_product")
@Getter
@Setter
@AllArgsConstructor
public class OrderProduct implements Serializable {

    @EmbeddedId
    private OrderProductPK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    private Product product;

    private int quantity;

    // Constructors
    public OrderProduct() {}

    public OrderProduct(Order order, Product product, int quantity) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.id = new OrderProductPK(order.getId(), product.getId());
    }
}
