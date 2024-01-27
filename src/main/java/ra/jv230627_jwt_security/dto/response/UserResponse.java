package ra.jv230627_jwt_security.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponse {
    private String id;
    private String userName;
    private String password;
    private String email;
    private String fullName;
    private boolean sex;
    private String phone;
    private Date birthDate;
}
