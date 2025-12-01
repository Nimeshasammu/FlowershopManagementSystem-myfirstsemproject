package lk.ijse.flowershop.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderPaymentDto {
    private int payment_id;
    private LocalDate payment_date;
    private LocalTime payment_time;
    private int order_id;
}
