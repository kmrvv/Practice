package ru.komarov.springtask.task.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;
import ru.komarov.springtask.task.confing.MyUserDetails;
import ru.komarov.springtask.task.dto.UserRegistration;
import ru.komarov.springtask.task.exception.UserNotFoundException;
import ru.komarov.springtask.task.dto.UserResponse;
import ru.komarov.springtask.task.entity.User;
import ru.komarov.springtask.task.mapper.UserMapper;
import ru.komarov.springtask.task.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService implements UserServiceInterface {
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, @Lazy BCryptPasswordEncoder passwordEncoder) {
        super();
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User save(UserRegistration userRegistration) {
        User user = new User(
                userRegistration.getName(),
                userRegistration.getEmail(),
                passwordEncoder.encode(userRegistration.getPassword()),
                userRegistration.getRole()
        );
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == ""){
            log.info("Username return null");
            return null;
        }
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User is null.");
        }
        return new MyUserDetails(user.get(), mapRoleToAuthorities(user.get().getRole()));
    }

    private Collection<? extends GrantedAuthority> mapRoleToAuthorities(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    public User createUser(User user) {
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    public User createUser() {
        return new User();
    }

    public User getUserById(Long userId) throws UserNotFoundException {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        return user;
    }

    public Collection<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserMapper::mapToUserDto).collect(Collectors.toList());
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
