package br.ufms.facom.onlinestorebackend.models;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class OrderProductPK implements Serializable {
    private Long orderId;
    private Long productId;

    // Constructors
    public OrderProductPK() {}

    public OrderProductPK(Long orderId, Long productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    // Equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderProductPK that = (OrderProductPK) o;
        return Objects.equals(orderId, that.orderId) &&
                Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId);
    }
}

