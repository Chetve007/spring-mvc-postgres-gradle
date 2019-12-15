package ru.alex.project.springapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alex.project.springapp.entities.User;

public interface UserRepo extends JpaRepository<User, Long> {

    User findByUsername(String username);

}