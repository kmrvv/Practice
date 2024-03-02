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
//        List<User> users = userRepository.findAll().stream().filter(user -> user.getEmail().equals(username)).toList();
//        User user = null;
//        for (User u: users) {
//            if (u.getEmail().equals(username)){
//                user = u;
//                break;
//            }
//        }
//        User user = users.get(0);
//        if (user == null) {
//            throw new UsernameNotFoundException("User null.");
//        }
//        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRoleToAuthorities(user.getRole()));

        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User is null.");
        }


        return new MyUserDetails(user.get(), mapRoleToAuthorities(user.get().getRole()));
//        return new org.springframework.security.core.userdetails.User(user.get().getEmail(),user.get().getPassword(), mapRoleToAuthorities(user.get().getRole()));
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

    public UserResponse updateUser(UserResponse user) {
        User existingUser = userRepository.findById(user.getId()).get();
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setRole(user.getRole());
        User updateUser = userRepository.save(existingUser);
        return UserMapper.mapToUserDto(updateUser);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
}
