package ru.komarov.springtask.task.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.komarov.springtask.task.dto.UserRegistration;
import ru.komarov.springtask.task.entity.User;

public interface UserServiceInterface extends UserDetailsService {
    User save(UserRegistration userRegistration);
}
