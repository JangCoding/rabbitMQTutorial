package org.example.rabbitmqtutorial.domain.user.repository;

import org.example.rabbitmqtutorial.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

}
