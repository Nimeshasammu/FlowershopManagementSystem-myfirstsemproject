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
    private int order_id;
}
