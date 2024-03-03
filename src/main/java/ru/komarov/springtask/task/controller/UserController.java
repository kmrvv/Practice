package ru.komarov.springtask.task.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.komarov.springtask.task.entity.User;
import ru.komarov.springtask.task.exception.UserNotFoundException;
import ru.komarov.springtask.task.service.UserService;


@AllArgsConstructor
@Controller
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public String viewHomePage(Model model) {
        model.addAttribute("listUsers", userService.getAllUsers());
        return "users/listOfUsers";
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

    @PostMapping("users/deleteUser/{id}")
    public String deleteUserPage(@PathVariable(value = "id") Long userId) {
        this.userService.deleteUser(userId);
        return "redirect:/users";
    }
}