package com.evgeny.kravchenko.shoppinglist.service;

import com.evgeny.kravchenko.shoppinglist.persist.User;
import com.evgeny.kravchenko.shoppinglist.persist.UserRepository;
import com.evgeny.kravchenko.shoppinglist.security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService implements UserDetailsService {

    private final UserRepository repo;

    @Autowired
    public UserAuthService(UserRepository repo) {
        this.repo = repo;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Пользователь не найден"));

        return SecurityUser.fromUser(user);
    }
}
