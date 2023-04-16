package com.cherkasov.services;

import com.cherkasov.dto.UserMainInfoDto;
import com.cherkasov.models.user.Role;
import com.cherkasov.models.user.User;
import com.cherkasov.repositories.userRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService, UserResource {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String NAME_COOKIE_ABOUT_AGE = "ageUser";


    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public void save(final User user) {
        if (user.getPassword() == null) {
            throw new IllegalArgumentException("Password is incorrect");
        }
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
        user.setRole(Role.CLIENT);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> getById(final String id) {
        return userRepository.findById(id);
    }

    public void setTypeById(final String id, final Role role) {
        userRepository.setTypeById(id, role);
    }

    public List<UserMainInfoDto> getUsersInfo() {
        return userRepository.getUsersInfo();
    }

    public Optional<User> findUserInfoByEmail(final String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public int ageUserByCookie(final Cookie[] cookies) {
        int age = 0;
        for (Cookie cookie : cookies) {
            if (cookie.getName().contains(NAME_COOKIE_ABOUT_AGE)) {
                age = Integer.parseInt(cookie.getValue());
            }
        }
        return age;
    }

    public Cookie createAgeCookie(final int age) {
        Cookie cookie = new Cookie(NAME_COOKIE_ABOUT_AGE, String.valueOf(age));
        cookie.setPath("/");
        cookie.setMaxAge(900);
        return cookie;
    }
}
