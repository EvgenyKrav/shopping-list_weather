package com.evgeny.kravchenko.shoppinglist.controller;

import com.evgeny.kravchenko.shoppinglist.persist.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class UsersControlPanelController {

    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Value("${admin.adminpanel.keyword}")
    private String key;

    private String encodeKey;

    @Autowired
    public UsersControlPanelController(UserRepository userRepository,
                                       ConfirmationTokenRepository confirmationTokenRepository) {
        this.userRepository = userRepository;
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @GetMapping("/users-list")
    public String usersList(@RequestParam("key") String key,
                            Model model) {

        if (encodeKey.equals(key)) {
            List<User> userList = userRepository.findAll();
            model.addAttribute("usersList", userList);

            return "users-list";
        }

        return "redirect:/";
    }

    @GetMapping
    public String getCheckAdminRole(
            @RequestParam("username") String username,
            @RequestParam("key") String key) {

        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Пользователь не найден"));

        if (user.getRole() == Role.ROLE_ADMIN) {
            if (key.equals(this.key)) {
                encodeKey = new BCryptPasswordEncoder().encode(key);
                return "redirect:/admin/users-list?key=" + encodeKey;
            }
        }
        return "redirect:/";
    }

    @PostMapping("/banned/{id}")
    public String bannedUser(@PathVariable("id") long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("Пользователь не найден"));

       user.setStatus(Status.BANNED);
       userRepository.save(user);
        encodeKey = new BCryptPasswordEncoder().encode(key);
        return "redirect:/admin/users-list?key=" + encodeKey;
    }

    @PostMapping("/activated/{id}")
    public String activateUser(@PathVariable("id") long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("Пользователь не найден"));

        user.setStatus(Status.ACTIVATED);
        userRepository.save(user);
        encodeKey = new BCryptPasswordEncoder().encode(key);
        return "redirect:/admin/users-list?key=" + encodeKey;
    }
}
