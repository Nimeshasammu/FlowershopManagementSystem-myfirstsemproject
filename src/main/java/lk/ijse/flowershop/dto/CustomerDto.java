package lk.ijse.flowershop.dto;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerDto {
    private String cus_id;
    private String cus_name;
    private String email;
    private String contact_num;
    private String address;
    private String register_date;

    @Override
    public String toString() {
        return cus_id + " - " + cus_name;
    }
}
