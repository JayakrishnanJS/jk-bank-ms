package com.jkbank.gatewayserver.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Converts Keycloak roles from JWT to Spring Security GrantedAuthority collection
public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        // Extract realm roles from the "realm_access" claim
        Map<String, Object> realmAccess = (Map<String, Object>) source.getClaims().get("realm_access");
        // If no roles are found, return an empty list
        if (realmAccess == null || realmAccess.isEmpty()) {
            return new ArrayList<>();
        }
        // Map roles to GrantedAuthority with "ROLE_" prefix
        Collection<GrantedAuthority> returnValue = ((List<String>) realmAccess.get("roles"))
                .stream().map(roleName -> "ROLE_" + roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return returnValue;
    }
}
