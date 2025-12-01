package lk.ijse.flowershop.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SupplierDto {
    private int supplier_id;
    private String name;
    private String email;
    private String contact_num;
    private String address;

}
