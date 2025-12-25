package lk.ijse.flowershop.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetailsDto {
    private String order_id;
    private String item_id;
    private int quantity;
    private  double unit_price;


}
