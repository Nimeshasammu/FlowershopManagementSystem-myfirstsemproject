package lk.ijse.flowershop.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeDto {
    private int emp_id;
    private String name;
    private String nic;
    private String job_role;
    private String email;
    private String contact_num;
    private String address;


}
