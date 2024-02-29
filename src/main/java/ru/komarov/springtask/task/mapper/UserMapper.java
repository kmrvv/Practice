package ru.komarov.springtask.task.mapper;

import ru.komarov.springtask.task.dto.UserResponse;
import ru.komarov.springtask.task.entity.User;

public class UserMapper {
    public static UserResponse mapToUserDto(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
    }

    public static User mapToUser(UserResponse userDto) {
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getRole()
        );
    }
}
