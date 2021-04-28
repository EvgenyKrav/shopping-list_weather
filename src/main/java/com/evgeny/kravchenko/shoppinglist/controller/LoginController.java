package com.evgeny.kravchenko.shoppinglist.controller;

import com.evgeny.kravchenko.shoppinglist.persist.*;
import com.evgeny.kravchenko.shoppinglist.service.EmailServiceImpl;
import com.evgeny.kravchenko.shoppinglist.service.UserRepr;
import com.evgeny.kravchenko.shoppinglist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class LoginController {

    private final UserService userService;
    private final EmailServiceImpl emailService;
    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Value("${server.address}")
    private String serverAddress;
    @Value("${server.port}")
    private String serverPort;

    @Autowired
    public LoginController(
            UserService userService,
            EmailServiceImpl emailService,
            UserRepository userRepository,
            ConfirmationTokenRepository confirmationTokenRepository) {

        this.userService = userService;
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/sign-up")
    public String signUpPage(Model model) {
        model.addAttribute("user", new UserRepr());
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpNewUser(@Valid @ModelAttribute("user") UserRepr userRepr, BindingResult bindingResult) {

        if (!userRepr.getPassword().equals(userRepr.getRepeatPassword())) {
            bindingResult.rejectValue("password", "error.password", "Пароли не совпадают");
            bindingResult.rejectValue("repeatPassword", "error.repeatPassword", "Пароли не совпадают");

            return "sign-up";
        }

        if (bindingResult.hasErrors()) {
            return "sign-up";
        }


        User user = userService.create(userRepr);

        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);

        emailService.sendSimpleMessage(
                user.getEmail(),
                "Подтверждение аккаунта",
                "Посетите ссылку, чтобы подтвердить ваш аккаунт: http://"
                        + serverAddress
                        + ":"
                        + serverPort
                        + "/shopping-list/confirm-account?token="
                        + confirmationToken.getConfirmationToken());

        System.out.println("Link to confirm account: http://100.123.67.115:8080/shopping-list/confirm-account?token=" +
                confirmationToken.getConfirmationToken());

        return "redirect:/login";
    }

    @GetMapping("/confirm-account")
    public String confirmUserAccount(@RequestParam("token") String confirmationToken) {

        Optional<ConfirmationToken> tokenDB = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        Optional<User> foundedUser;
        User user;

        if (tokenDB.isPresent()) {
            foundedUser = userRepository.findByUsername(tokenDB.get().getUser().getUsername());

            if (foundedUser.isPresent()) {
                user = foundedUser.get();
                user.setIsEnable("true");
                user.setStatus(Status.ACTIVATED);

                userRepository.save(user);

                return "redirect:/login";
            }
        }

        return "redirect:/sign-up";
    }
}
