package com.evgeny.kravchenko.shoppinglist.persist;

import lombok.Value;

public enum ClothesSet {
    VERY_COLD(Integer.MIN_VALUE, -50,
            "Шапку", "Очень теплую куртку",
            "Очень теплые брюки", "Зимнюю обувь",
            "Перчатки, шарф"),
    MEDIUM_COLD(-49, -20,
            "Шапку", "Теплую куртку",
            "Брюки", "Зимнюю обувь",
            "Перчатки, шарф"),
    NOT_COLD(-19, 5,
            "Шапку", "Куртку",
            "Брюки", "Ботинки",
            ""),
    VERY_HOT(30, Integer.MAX_VALUE,
            "Кепку", "Майку, футболку",
            "Шорты", "Сланцы",
            "Очки"),
    MEDIUM_HOT(15, 29,
            "", "Футболку",
            "Джинсы или шорты", "Кроссовки",
            "Очки"),
    NOT_HOT(6, 14,
            "", "Футболку, рубашку",
            "Брюки", "Кроссовки",
            ""),
    NOT_FOUND();

    private int lowestTemp;
    private int highestTemp;
    private String headdress;
    private String outwear;
    private String bottomOfClothing;
    private String shoes;
    private String accessories;

    ClothesSet() {}

    ClothesSet(int lowestTemp, int highestTemp,
               String headdress, String outwear,
               String bottomOfClothing, String shoes,
               String accessories) {

        this.lowestTemp = lowestTemp;
        this.highestTemp = highestTemp;
        this.headdress = headdress;
        this.outwear = outwear;
        this.bottomOfClothing = bottomOfClothing;
        this.shoes = shoes;
        this.accessories = accessories;
    }

    public int getLowestTemp() {
        return lowestTemp;
    }

    public int getHighestTemp() {
        return highestTemp;
    }

    public String getHeaddress() {
        return headdress;
    }

    public String getOutwear() {
        return outwear;
    }

    public String getBottomOfClothing() {
        return bottomOfClothing;
    }

    public String getShoes() {
        return shoes;
    }

    public String getAccessories() {
        return accessories;
    }

    public static ClothesSet getSet(int temperature, String weatherDescription,
                                    int windSpeed, String windDirection) {

        for (ClothesSet set: values()) {
            if (temperature >= set.getLowestTemp() && temperature <= set.getHighestTemp()) {
                return set;
            }
        }

        return NOT_FOUND;
    }

    @Override
    public String toString() {
        return "ClothesSet{" +
                "lowestTemp=" + lowestTemp +
                ", highestTemp=" + highestTemp +
                ", headdress='" + headdress + '\'' +
                ", outwear='" + outwear + '\'' +
                ", bottomOfClothing='" + bottomOfClothing + '\'' +
                ", shoes='" + shoes + '\'' +
                ", accessories='" + accessories + '\'' +
                '}';
    }
}













