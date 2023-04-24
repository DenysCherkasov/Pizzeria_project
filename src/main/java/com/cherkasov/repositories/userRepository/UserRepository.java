package com.cherkasov.repositories.userRepository;

import com.cherkasov.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository <User, String>, CustomizedUserRepository {
    @Modifying
    void deleteByEmailIgnoreCase(final String email);

    Optional<User> findUserByEmail(String email);

}
