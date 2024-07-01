package br.ufms.facom.onlinestorebackend.models;

public enum OrderStatus {
    PENDING,
    PROCESSING,
    SHIPPED,
    COMPLETED,
    CANCELED,
    REFUNDED;

    public static OrderStatus fromValue(Integer status) {
        return switch (status) {
            case 0 -> PENDING;
            case 1 -> PROCESSING;
            case 2 -> SHIPPED;
            case 3 -> COMPLETED;
            case 4 -> CANCELED;
            case 5 -> REFUNDED;
            default -> throw new IllegalArgumentException("Invalid status value: " + status);
        };
    }
}
