package com.dev.controller;

import com.dev.constants.EAuthRole;
import com.dev.entities.AuthRole;
import com.dev.entities.User;
import com.dev.model.LoginRequest;
import com.dev.model.ResponseHandler;
import com.dev.model.SignUpRequest;
import com.dev.repository.AuthRoleRepository;
import com.dev.repository.UserRepository;
import com.dev.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthRoleRepository authRoleRepository;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest login){
        User user = new User();
        user.setUsername(login.getUsername());
        user.setPassword(login.getPassword());
        return customerService.customerPasswordAuthentication(user);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(ResponseHandler.generateResponse("Username is already taken!", HttpStatus.IM_USED,null));
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(ResponseHandler.generateResponse("Error: Email is already in use!",HttpStatus.IM_USED,null));
        }
        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));
        Set<String> strRoles = signUpRequest.getRole();
        Set<AuthRole> roles = new HashSet<>();
        if (strRoles == null) {
            AuthRole userRole = authRoleRepository.findByName(EAuthRole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        AuthRole adminRole = authRoleRepository.findByName(EAuthRole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "mod":
                        AuthRole guestRole = authRoleRepository.findByName(EAuthRole.ROLE_GUEST)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(guestRole);
                        break;
                    default:
                        AuthRole userRole = authRoleRepository.findByName(EAuthRole.ROLE_USER
                                .ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(ResponseHandler.generateResponse("User Create Successfully",HttpStatus.OK,null));
    }
}
