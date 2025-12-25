package lk.ijse.flowershop.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemDto {
    private String item_id;
    private String item_name;
    private double unit_price;
    private String img_src;
    private String item_color;
    private int quantity;

    @Override
    public String toString() {
        return item_id + " - " + item_name;
    }
}
