package com.cherkasov.models.user;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    CLIENT,
    MANAGER;

    @Override
    public String getAuthority() {
        return name();
    }

}
