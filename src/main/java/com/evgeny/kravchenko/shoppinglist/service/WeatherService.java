package com.evgeny.kravchenko.shoppinglist.service;

import com.evgeny.kravchenko.shoppinglist.persist.ClothesSet;
import com.evgeny.kravchenko.shoppinglist.persist.JSONWeather;
import com.evgeny.kravchenko.shoppinglist.persist.Wind;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Service
public class WeatherService {

    @Value("${weather.api.url}")
    private String weatherApiUrl;
    @Value("${weather.api.params}")
    private String urlParams;
    @Value("${weather.api.key}")
    private String apiKey;

    private URL url;
    private URLConnection urlConnection;
    private StringBuffer weatherJson;
    private JSONWeather weather;
    private JSONObject jsonObject;

    public JSONWeather getWeather() {
        return weather;
    }

    public void weatherIn(String city) {

        setOnConnection(weatherApiUrl + city + urlParams + apiKey);

        if (getJson(urlConnection)) {
            jsonObject = new JSONObject(weatherJson.toString());
            setValues();
        } else {
            weather.setStatusCode(404);
        }
        System.out.println(weather.toString());
    }

    private void setOnConnection(String urlAddress) {
        try {
            url = new URL(urlAddress);
        } catch (MalformedURLException e) {
            System.err.println("Incorrect url address!");
        }

        try {
            urlConnection = url.openConnection();
        } catch (IOException e) {
            System.err.println("Can't connect to weather service!");
        }
        System.out.println("Success!");
    }

    private boolean getJson(URLConnection urlConnection) {
        weatherJson = new StringBuffer();

        String line;
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
            while ((line = reader.readLine()) != null) {
                weatherJson.append(line);
            }
        } catch (IOException e) {
            System.err.println("Error while reading json!");
            return false;
        }

        System.out.println(weatherJson.toString());

        return true;
    }

    private void setValues() {

        JSONObject main = jsonObject.getJSONObject("main");
        JSONObject sys = jsonObject.getJSONObject("sys");
        JSONObject wind = jsonObject.getJSONObject("wind");

        weather = JSONWeather.builder()
                .cityName(jsonObject.getString("name"))
                .description(jsonObject.getJSONArray("weather").getJSONObject(0).getString("description"))
                .statusCode(jsonObject.getInt("cod"))
                .unixTime(jsonObject.getInt("dt"))
                .unixTimeZone(jsonObject.getInt("timezone"))
                .visibility(jsonObject.getInt("visibility") / 1000)
                .feelsLike((int) main.getDouble("feels_like"))
                .humidity(main.getInt("humidity"))
                .temperature(main.getInt("temp"))
                .temperatureMax(main.getInt("temp_max"))
                .temperatureMin(main.getInt("temp_min"))
                .sunRise(sys.getLong("sunrise"))
                .sunSet(sys.getLong("sunset"))
                .id(sys.getInt("id"))
                .windSpeed(wind.getInt("speed"))
                .windDirection(Wind.getWind((float) wind.getInt("deg")).getDirection())
                .build();

        long unixTimeZone = weather.getUnixTimeZone();
        Date date = new Date((weather.getUnixTime() + unixTimeZone) * 1000L);
        Date sunSetDate = new Date((weather.getSunSet() + unixTimeZone) * 1000L);
        Date sunRiseDate = new Date((weather.getSunRise() + unixTimeZone) * 1000L);

        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT" + (unixTimeZone / 6)));

        String formattedDate = sdf.format(date);
        String sunSetFormattedDate = sdf.format(sunSetDate);
        String sunRiseFormattedDate = sdf.format(sunRiseDate);

        weather.setDay(
                Integer.parseInt(formattedDate) >= Integer.parseInt(sunRiseFormattedDate) &&
                Integer.parseInt(formattedDate) <= Integer.parseInt(sunSetFormattedDate));


        weather.setClothesSet(ClothesSet.getSet(
                weather.getTemperature(),
                weather.getDescription(),
                weather.getWindSpeed(),
                weather.getWindDirection()
        ));

        System.out.println(formattedDate);
        System.out.println("Sun rise time(HH): " + sunRiseFormattedDate);
        System.out.println("Sun set time(HH): " + sunSetFormattedDate);
        System.out.println(Integer.parseInt(formattedDate));
    }
}
