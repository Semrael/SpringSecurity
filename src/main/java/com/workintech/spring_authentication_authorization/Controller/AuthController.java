package com.workintech.spring_authentication_authorization.Controller;


import com.workintech.spring_authentication_authorization.dto.RegisterUser;
import com.workintech.spring_authentication_authorization.entity.ApplicationUser;
import com.workintech.spring_authentication_authorization.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ApplicationUser register(@RequestBody RegisterUser registerUser){
        return authenticationService.register(registerUser.fullName(),registerUser.email(),registerUser.password());
    }
}
