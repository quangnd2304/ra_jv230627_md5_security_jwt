package ra.jv230627_jwt_security.serviceImp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.jv230627_jwt_security.dto.request.SignUpRequest;
import ra.jv230627_jwt_security.dto.response.SignUpResponse;
import ra.jv230627_jwt_security.model.ERoles;
import ra.jv230627_jwt_security.model.Roles;
import ra.jv230627_jwt_security.model.Users;
import ra.jv230627_jwt_security.repository.RolesRepository;
import ra.jv230627_jwt_security.repository.UsersRepository;
import ra.jv230627_jwt_security.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Service
public class UsersServiceImp implements UserService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public SignUpResponse register(SignUpRequest signUpRequest) {
        Set<Roles> setRoles = new HashSet<>();
        signUpRequest.getListRoles().forEach(role -> {
            //admin, moderator, user
            switch (role) {
                case "admin":
                    setRoles.add(rolesRepository.findByName(ERoles.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Không tồn tại quyền admin")));
                    break;
                case "moderator":
                    setRoles.add(rolesRepository.findByName(ERoles.ROLE_MODERATOR)
                            .orElseThrow(() -> new RuntimeException("Không tồn tại quyền admin")));
                    break;
                case "user":
                default:
                    setRoles.add(rolesRepository.findByName(ERoles.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Không tồn tại quyền admin")));
            }
        });
        Users user = modelMapper.map(signUpRequest, Users.class);
        user.setListRoles(setRoles);
        //Mã hóa mật khẩu khi đăng ký
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //Thực hiện thêm mới
        Users userCreated = usersRepository.save(user);
        return modelMapper.map(userCreated, SignUpResponse.class);
    }
}
