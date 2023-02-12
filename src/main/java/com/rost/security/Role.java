package com.rost.security;

import org.thymeleaf.util.StringUtils;

public enum Role {
    ROLE_GUEST,
    ROLE_USER,
    ROLE_ADMIN;

    public String roleName() {
        return StringUtils.substringAfter(name(), "_");
    }
}
