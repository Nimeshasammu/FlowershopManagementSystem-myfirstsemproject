package lk.ijse.flowershop.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {
    private int user_id;
    private  String user_name;
    private String password;
    private String email;
    private int emp_id;
}
