package school_management.security.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import school_management.security.service.LoginService;

@RestController
@AllArgsConstructor
public class LoginController {


    private final LoginService loginService;

    @PostMapping("/login")
    public String login(@RequestBody @Valid LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        return loginService.login(loginRequest, request, response);
    }

}
