package lk.ijse.flowershop.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentTM {
    private String paymentId;
    private String orderId;
    private String paymentMethod;
    private double totalAmount;
    private LocalDateTime paymentDate;
    private String customerId;
    private String status;
}
