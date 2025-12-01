package lk.ijse.flowershop.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ItemDto {
    private  int item_id;
    private String item_name;
    private double unit_price;
    private int quantity;
}
