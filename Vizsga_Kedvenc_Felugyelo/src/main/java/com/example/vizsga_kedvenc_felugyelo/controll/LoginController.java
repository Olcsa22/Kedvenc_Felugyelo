package com.example.vizsga_kedvenc_felugyelo.controll;

import com.example.vizsga_kedvenc_felugyelo.model.User;
import com.example.vizsga_kedvenc_felugyelo.repository.UserRepository;
import com.example.vizsga_kedvenc_felugyelo.service.UserService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:4200")
public class LoginController
{
    @Autowired
    private final UserService userService;

    public LoginController(UserService userRepository)
    {
        this.userService = userRepository;
    }

    @RequestMapping(value = "/login")
    public User login(HttpServletRequest request)
    {
            User user1 = userService.findByUsername(request.getUserPrincipal().getName());
            return user1;
    }
}
