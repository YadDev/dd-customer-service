package com.dev.services.impl;

import com.dev.entities.User;
import com.dev.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DBDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DBDetailService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return build(user);
    }
    private UserDetails build(User user){
        try {
            ArrayList<GrantedAuthority> grantedAuthorities =new ArrayList<>();
            grantedAuthorities.add(new SimpleGrantedAuthority("admin"/*user.getRoles().toString()*/));
            return new org.springframework.security.core.userdetails.User(user.getUsername(),
                    passwordEncoder.encode(user.getPassword()),
                    grantedAuthorities);

        }
        catch (Exception e){
            throw  null;
        }
    }
}
