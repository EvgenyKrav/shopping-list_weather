package com.evgeny.kravchenko.shoppinglist.persist;

public enum Wind {

    N("С", 348.76f, 11.25f),
    NNE("ССВ", 11.26f, 33.75f),
    NE("СВ", 33.76f, 56.25f),
    ENE("ВСВ", 56.26f, 78.75f),
    E("В", 78.75f, 101.25f),
    ESE("ВЮВ", 101.26f, 123.75f),
    SE("ЮВ", 123.76f, 146.25f),
    SSE("ЮЮВ", 146.26f, 168.75f),
    S("Ю", 168.76f, 191.25f),
    SSW("ЮЮЗ", 191.26f, 213.75f),
    SW("ЮЗ", 213.76f, 236.25f),
    WSW("ЗЮЗ", 236.26f, 258.75f),
    W("З", 258.76f, 281.25f),
    WNW("ЗСЗ", 281.26f, 303.75f),
    NW("СЗ", 303.76f, 326.25f),
    NNW("ССЗ", 326.26f, 348.75f),
    NOT_FOUND();

    private String direction;
    private float startDegree;
    private float endDegree;

    Wind(String direction, float startDegree, float endDegree) {
        this.direction = direction;
        this.startDegree = startDegree;
        this.endDegree = endDegree;
    }
    
    Wind() {}

    public static Wind getWind(float degree) {

        for (Wind currentWind: values()) {
            if (currentWind.getStartDegree() <= degree && currentWind.getEndDegree() >= degree) {
                return currentWind;
            }
        }

        return NOT_FOUND;
    }

    public String getDirection() {
        return direction;
    }

    public float getStartDegree() {
        return startDegree;
    }

    public float getEndDegree() {
        return endDegree;
    }
}
