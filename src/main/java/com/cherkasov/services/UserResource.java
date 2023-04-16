package com.cherkasov.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserResource {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
