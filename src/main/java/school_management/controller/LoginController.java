package school_management.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import school_management.service.LoginService;

@Controller
@AllArgsConstructor
public class LoginController {


    private final LoginService loginService;

    @PostMapping("/login")
    public String login(@RequestBody @Valid LoginRequest loginRequest) {
        return loginService.login(loginRequest);
    }

}
