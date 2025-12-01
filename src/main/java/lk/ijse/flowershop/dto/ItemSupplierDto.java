package lk.ijse.flowershop.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ItemSupplierDto {
    private  int item_id;
    private  int supplier_id;
    private double new_price;
    private LocalDate date;
    private LocalTime time;
}
