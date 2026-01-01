package lk.ijse.flowershop.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PaymentDto {
    private int payment_id;
    private String payment_method;   // Cash, Card, Online
    private LocalDateTime payment_date;
    private double total_amount;
    private String status;            // Success, Failed, Pending
    private String order_id;

    public PaymentDto(String paymentMethod, LocalDateTime paymentDate, double totalPrice, String status, String orderId) {
        this.payment_method = paymentMethod;
        this.payment_date = paymentDate;
        this.total_amount = totalPrice;
        this.status = status;
        this.order_id = orderId;
    }
}
