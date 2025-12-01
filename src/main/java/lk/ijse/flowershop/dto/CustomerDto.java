package lk.ijse.flowershop.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomerDto {
    private int cus_id;
    private String cus_name;
    private String email;
    private String contact_num;
    private String address;
}
