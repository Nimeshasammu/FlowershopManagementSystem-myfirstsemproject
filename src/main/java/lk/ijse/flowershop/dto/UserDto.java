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
    private String role;
    private int emp_id;

    public UserDto(String userName, String password, String email, String role) {
        this.user_name = userName;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
