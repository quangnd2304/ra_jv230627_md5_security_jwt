package ra.jv230627_jwt_security.serviceImp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.jv230627_jwt_security.dto.request.SignInRequest;
import ra.jv230627_jwt_security.dto.request.SignUpRequest;
import ra.jv230627_jwt_security.dto.response.SignInResponse;
import ra.jv230627_jwt_security.dto.response.SignUpResponse;
import ra.jv230627_jwt_security.dto.response.UserResponse;
import ra.jv230627_jwt_security.model.ERoles;
import ra.jv230627_jwt_security.model.Roles;
import ra.jv230627_jwt_security.model.Users;
import ra.jv230627_jwt_security.repository.RolesRepository;
import ra.jv230627_jwt_security.repository.UsersRepository;
import ra.jv230627_jwt_security.sercurity.jwt.JwtProvider;
import ra.jv230627_jwt_security.sercurity.principle.CustomUserDetail;
import ra.jv230627_jwt_security.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;

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
        //Khi đăng ký, set status mặc định là true
        user.setStatus(true);
        //Thực hiện thêm mới
        Users userCreated = usersRepository.save(user);
        SignUpResponse signUpResponse = modelMapper.map(userCreated, SignUpResponse.class);
        //set lại quyền user trả về
        List<String> listUserRoles = new ArrayList<>();
        userCreated.getListRoles().stream().forEach(roles -> {
            listUserRoles.add(roles.getName().name());
        });
        signUpResponse.setListRoles(listUserRoles);
        return signUpResponse;
    }

    @Override
    public SignInResponse login(SignInRequest signInRequest) {
        //1. Lấy User trong db ra và so sánh(mã hóa mật khẩu Bcryt)
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    signInRequest.getUserName(), signInRequest.getPassword()));
        } catch (Exception ex) {
            throw new RuntimeException("Username or Password incorrect");
        }
        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
        String accessToken = jwtProvider.generateAccessToken(userDetails);
        String refreshToken = jwtProvider.generateRefreshToken(userDetails);
        return new SignInResponse(userDetails.getUsername(), userDetails.getPassword(),
                userDetails.getEmail(), userDetails.getFullName(),
                userDetails.getAuthorities(), accessToken, refreshToken);
    }

    @Override
    public List<UserResponse> findAll() {
        List<Users> listUser = usersRepository.findAll();
        List<UserResponse> listUserResponse = listUser.stream()
                .map(users -> modelMapper.map(users, UserResponse.class)).collect(Collectors.toList());
        return listUserResponse;
    }
}
