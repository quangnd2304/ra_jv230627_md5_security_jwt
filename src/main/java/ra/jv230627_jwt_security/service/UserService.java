package ra.jv230627_jwt_security.service;

import ra.jv230627_jwt_security.dto.request.SignUpRequest;
import ra.jv230627_jwt_security.dto.response.SignUpResponse;

public interface UserService {
    SignUpResponse register(SignUpRequest signUpRequest);
}
