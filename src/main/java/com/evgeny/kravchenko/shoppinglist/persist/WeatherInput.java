package com.evgeny.kravchenko.shoppinglist.persist;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class WeatherInput {

    @NotBlank
    String city;

}
