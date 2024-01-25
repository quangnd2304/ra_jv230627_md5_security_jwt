package ra.jv230627_jwt_security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.jv230627_jwt_security.dto.request.SignUpRequest;
import ra.jv230627_jwt_security.dto.response.SignUpResponse;
import ra.jv230627_jwt_security.service.UserService;

@RestController
@RequestMapping("api/v1/")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("public/user")
    public ResponseEntity<SignUpResponse> register(@RequestBody SignUpRequest signUpRequest) {
        SignUpResponse signUpResponse = userService.register(signUpRequest);
        return new ResponseEntity<>(signUpResponse, HttpStatus.CREATED);
    }
}
