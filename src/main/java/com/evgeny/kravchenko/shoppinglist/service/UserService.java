package com.evgeny.kravchenko.shoppinglist.service;

import com.evgeny.kravchenko.shoppinglist.persist.Role;
import com.evgeny.kravchenko.shoppinglist.persist.Status;
import com.evgeny.kravchenko.shoppinglist.persist.User;
import com.evgeny.kravchenko.shoppinglist.persist.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repo, BCryptPasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(UserRepr userRepr) {
        User user = new User();
        user.setEmail(userRepr.getEmail());
        user.setUsername(userRepr.getUsername());
        user.setPassword(passwordEncoder.encode(userRepr.getPassword()));
        user.setRole(Role.ROLE_USER);
        user.setStatus(Status.BANNED);

        repo.save(user);

        return user;
    }
}
