package lk.ijse.flowershop.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDto {
    private String order_id;
    private LocalDate order_date;
    private LocalTime time;
    private double total_amount;
    private String cus_id;
    private  String user_id;
    private ArrayList<OrderDetailsDto> cartList;

}
