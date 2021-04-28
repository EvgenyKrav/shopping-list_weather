package com.evgeny.kravchenko.shoppinglist.controller;

import com.evgeny.kravchenko.shoppinglist.persist.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class ShoppingListController {

    private final ShoppingItemRepository shoppingItemRepository;
    private final UserRepository userRepository;

    @Autowired
    public ShoppingListController(ShoppingItemRepository shoppingItemRepository, UserRepository userRepository) {
        this.shoppingItemRepository = shoppingItemRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String indexPage(Model model, Principal principal) {

        model.addAttribute("username", principal.getName());
        model.addAttribute("items", shoppingItemRepository.findByUserUsername(principal.getName()));
        model.addAttribute("item", new ShoppingItem());

        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        if (user.getStatus() == Status.BANNED) {
            return "redirect:/logout";
        }
        return "index";
    }

    @PostMapping
    public String newShoppingItem(
            @Valid @ModelAttribute("item") ShoppingItem item,
            Principal principal,
            BindingResult bindingResult) throws InterruptedException {

        Thread.sleep(1000);

        if (bindingResult.hasErrors()) {
            return "index";
        }

        if (!item.getName().isBlank()) {
            User user = userRepository.findByUsername(principal.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

            item.setUser(user);
            shoppingItemRepository.save(item);
        }

        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteShoppingItem(@PathVariable("id") long id) throws InterruptedException {

        Thread.sleep(1000);

        shoppingItemRepository.deleteById(id);

        return "redirect:/";
    }

    @DeleteMapping("/deleteAll")
    public String deleteAllShoppingItems() throws InterruptedException {

        Thread.sleep(1000);

        shoppingItemRepository.deleteAll();

        return "redirect:/";
    }
}
