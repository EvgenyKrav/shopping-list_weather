package com.evgeny.kravchenko.shoppinglist.persist;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JSONWeather {

    private String description;
    private int temperature;
    private int feelsLike;
    private int temperatureMin;
    private int temperatureMax;
    private int humidity;
    private int visibility;
    private String cityName;
    private long unixTime;
    private long unixTimeZone;
    private long sunRise;
    private long sunSet;
    private int statusCode;
    private boolean isDay;
    private int id;
    private String windDirection;
    private int windSpeed;
    private ClothesSet clothesSet;

}
