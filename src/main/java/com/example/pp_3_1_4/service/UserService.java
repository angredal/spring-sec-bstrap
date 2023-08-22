package com.example.pp_3_1_4.service;

import com.example.pp_3_1_4.model.User;
import com.example.pp_3_1_4.repository.RoleRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;
import java.util.Optional;


public interface UserService extends UserDetailsService{
    User findUserByEmail(String email);
    List<User> findAll();
    Optional<User> findById(Long id);
    void saveUser(User user);
    void deleteById(Long id);
    RoleRepository getRoleRepository();
    void setPasswordEncoder(PasswordEncoder passwordEncoder);
    PasswordEncoder getPasswordEncoder();
}
