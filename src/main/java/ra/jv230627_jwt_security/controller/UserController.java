package ra.jv230627_jwt_security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.jv230627_jwt_security.dto.request.SignInRequest;
import ra.jv230627_jwt_security.dto.request.SignUpRequest;
import ra.jv230627_jwt_security.dto.response.SignInResponse;
import ra.jv230627_jwt_security.dto.response.SignUpResponse;
import ra.jv230627_jwt_security.dto.response.UserResponse;
import ra.jv230627_jwt_security.service.UserService;

import java.util.List;

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

    @PostMapping("public/user/login")
    public ResponseEntity<SignInResponse> login(@RequestBody SignInRequest signInRequest) {
        return new ResponseEntity<>(userService.login(signInRequest), HttpStatus.OK);
    }
    @GetMapping("/admin/user")
    public ResponseEntity<List<UserResponse>> findALl(){
        return new ResponseEntity<>(userService.findAll(),HttpStatus.OK);
    }
}
