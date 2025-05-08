package org.example.rabbitmqtutorial.domain.user.repository;

import org.example.rabbitmqtutorial.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUserId(Long userId);
}
