package com.hotel.web.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hotel.web.Entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
