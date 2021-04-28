package com.evgeny.kravchenko.shoppinglist.persist;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Role {
    ROLE_USER(Stream.of(
            Permission.STANDARD
    ).collect(Collectors.toSet())),

    ROLE_ADMIN(Stream.of(
            Permission.STANDARD,
            Permission.DEVELOPER
    ).collect(Collectors.toSet()));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
