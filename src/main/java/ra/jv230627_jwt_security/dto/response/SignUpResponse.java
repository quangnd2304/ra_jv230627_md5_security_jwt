package ra.jv230627_jwt_security.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignUpResponse {
    private String id;
    private String userName;
    private String password;
    private String email;
    private String fullName;
    private boolean sex;
    private String phone;
    private Date birthDate;
    private List<String> listRoles;
}
