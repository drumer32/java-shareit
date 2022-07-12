package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.exceptions.AlreadyExistException;
import ru.practicum.shareit.exceptions.ObjectNotFoundException;
import ru.practicum.shareit.exceptions.ObjectNotValidException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDto;

import java.util.Collection;
import java.util.Map;

public interface UserRepository {

    UserDto createUser(UserDto userDto) throws ObjectNotValidException;

    UserDto updateUser(UserDto userDto, Long id) throws AlreadyExistException, ObjectNotFoundException;

    void deleteUser(Long id) throws ObjectNotFoundException;

    UserDto getUserById(Long id) throws ObjectNotFoundException;

    Collection<UserDto> getAllUsers();

    Map<Long, User> getUsers();
}
