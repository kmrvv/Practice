package ru.komarov.springtask.task.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.komarov.springtask.task.dto.UserResponse;
import ru.komarov.springtask.task.entity.User;
import ru.komarov.springtask.task.exception.UserNotFoundException;
import ru.komarov.springtask.task.service.UserService;

import java.util.Collection;

@AllArgsConstructor
@Controller
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public String viewHomePage(Model model) {
        model.addAttribute("listUsers", userService.getAllUsers());
        return "users/listOfUsers";
    }

    @GetMapping("users/newUser")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", userService.createUser());
        return "users/newUser";
    }

    @PostMapping("users/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.createUser(user);
        return "redirect:/users";
    }

    @GetMapping("users/newUser/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") Long userId, Model model) throws UserNotFoundException {
        model.addAttribute("user", userService.getUserById(userId));
        return "users/userUpdate";
    }

    @GetMapping("users/deleteUser/{id}")
    public String deleteUserPage(@PathVariable(value = "id") Long userId) {
        this.userService.deleteUser(userId);
        return "redirect:/users";
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Collection<UserResponse>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getUserById(@PathVariable("id") Long userId) {
        try {
            UserResponse user = userService.getUserById(userId).mapToDtoResponse();
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }

//    @PutMapping("{id}")
//    public ResponseEntity<UserResponse> updateUser(@PathVariable("id") Long userId, @RequestBody UserResponse user) {
//        user.setId(userId);
//        UserResponse updateUser = userService.updateUser(user);
//        return new ResponseEntity<>(updateUser, HttpStatus.OK);
//    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>("Person deleted", HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllUsers() {
        userService.deleteAllUsers();
        return new ResponseEntity<>("All users deleted", HttpStatus.OK);
    }


}
