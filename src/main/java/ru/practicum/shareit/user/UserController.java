package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exceptions.AlreadyExistException;
import ru.practicum.shareit.exceptions.ObjectNotFoundException;
import ru.practicum.shareit.exceptions.ObjectNotValidException;
import ru.practicum.shareit.user.model.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDto createUser(@Validated @RequestBody UserDto userDto) throws AlreadyExistException, ObjectNotValidException {
        return userService.createUser(userDto);
    }

    @PatchMapping(value = {"/{id}"})
    public UserDto updateUser(@Validated @RequestBody UserDto userDto, @PathVariable Long id)
            throws ObjectNotFoundException, AlreadyExistException {
        return userService.updateUser(userDto, id);
    }

    @DeleteMapping(value = {"/{id}"})
    public void deleteUser(@PathVariable Long id) throws ObjectNotFoundException {
        userService.deleteUser(id);
    }

    @GetMapping(value = {"/{id}"})
    public UserDto getUserById(@PathVariable Long id) throws ObjectNotFoundException {
        return userService.getUserById(id);
    }

    @GetMapping
    public Collection<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }
}
