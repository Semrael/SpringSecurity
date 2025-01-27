package com.workintech.spring_authentication_authorization.service;

import com.workintech.spring_authentication_authorization.entity.ApplicationUser;
import com.workintech.spring_authentication_authorization.entity.Role;
import com.workintech.spring_authentication_authorization.repository.RoleRepository;
import com.workintech.spring_authentication_authorization.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ApplicationUser register(String fullName,String email,String password){
        String encodedPassword=passwordEncoder.encode(password);
        Role userRole=roleRepository.findByAuthority("USER") .orElseThrow(() -> new RuntimeException("Role 'USER' not found"));//.get();

        Set<Role> roles=new HashSet<>();
        roles.add(userRole);

        ApplicationUser user=new ApplicationUser();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setAuthorities(roles);

        return userRepository.save(user);
    }
}
