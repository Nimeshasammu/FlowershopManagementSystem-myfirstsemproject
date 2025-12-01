package lk.ijse.flowershop.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrdersDto {
    private int order_id;
    private LocalDate order_date;
    private LocalTime time;
    private double total_amount;
    private int cus_id;
    private  int user_id;
}
