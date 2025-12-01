package lk.ijse.flowershop.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderItemDto {
    private int order_id;
    private int item_id;
    private int quantity;
    private  int payment_id;

}
