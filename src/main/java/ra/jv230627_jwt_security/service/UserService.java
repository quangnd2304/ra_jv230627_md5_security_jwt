package ra.jv230627_jwt_security.service;

import ra.jv230627_jwt_security.dto.request.SignInRequest;
import ra.jv230627_jwt_security.dto.request.SignUpRequest;
import ra.jv230627_jwt_security.dto.response.SignInResponse;
import ra.jv230627_jwt_security.dto.response.SignUpResponse;
import ra.jv230627_jwt_security.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    SignUpResponse register(SignUpRequest signUpRequest);
    SignInResponse login(SignInRequest signInRequest);
    List<UserResponse> findAll();
}
