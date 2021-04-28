package com.evgeny.kravchenko.shoppinglist.controller;

import com.evgeny.kravchenko.shoppinglist.persist.ClothesSet;
import com.evgeny.kravchenko.shoppinglist.persist.JSONWeather;
import com.evgeny.kravchenko.shoppinglist.persist.WeatherInput;
import com.evgeny.kravchenko.shoppinglist.service.WeatherService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
@RequestMapping(value = "/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public String weather(Model model) {
        model.addAttribute("city_input", new WeatherInput());
        return "search";
    }

    @GetMapping("/city")
    public String weatherShow(@RequestParam("id") int id, Model model, HttpServletRequest request) {

        System.out.println("User ADDR & PORT-> " + request.getRemoteAddr() + ":" + request.getRemotePort());
        System.out.println("User HOST-> " + request.getRemoteHost());
        System.out.println("User USER-> " + request.getRemoteUser());

        JSONWeather jsonWeather = weatherService.getWeather();
        ClothesSet clothesSet;

        if (jsonWeather != null) {
            if (id == jsonWeather.getId()) {

                clothesSet = jsonWeather.getClothesSet();

                model.addAttribute("city_input", new WeatherInput());
                model.addAttribute("weather", jsonWeather);
                model.addAttribute("clothes_set", clothesSet);

                System.out.println(clothesSet.toString());
                return "weather";
            }
        }

        return "redirect:/weather";
    }

    @PostMapping
    public String weatherIn(@Valid @ModelAttribute("city_input") WeatherInput input, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "weather";
        }

        System.out.println(URLDecoder.decode(input.getCity(), StandardCharsets.UTF_8));

        weatherService.weatherIn(input.getCity());
        return "redirect:/weather/city?id=" + weatherService.getWeather().getId();
    }
}
