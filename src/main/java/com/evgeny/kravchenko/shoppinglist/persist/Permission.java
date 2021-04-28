package com.evgeny.kravchenko.shoppinglist.persist;

public enum Permission {
    STANDARD("standard"),
    DEVELOPER("developer");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
