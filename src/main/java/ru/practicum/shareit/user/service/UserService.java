package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.AlreadyExistException;
import ru.practicum.shareit.exceptions.ObjectNotFoundException;
import ru.practicum.shareit.exceptions.ObjectNotValidException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDto;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto createUser(UserDto userDto) throws AlreadyExistException, ObjectNotValidException {
        List<User> usersList = new ArrayList<>(userRepository.getUsers().values());
        for (User user : usersList) {
            if (user.getEmail().equals(userDto.getEmail())) {
                log.error("Пользователь с таким email {} уже существует ", userDto.getEmail());
                throw new AlreadyExistException("Пользователь с таким email уже существует");
            }
        }
        return userRepository.createUser(userDto);
    }

    public UserDto updateUser(UserDto userDto, Long id) throws ObjectNotFoundException, AlreadyExistException {
        if (userRepository.getUsers().containsKey(id)) {
            List<User> usersList = new ArrayList<>(userRepository.getUsers().values());
            for (User user : usersList) {
                if (user.getEmail().equals(userDto.getEmail()) && !user.getId().equals(id)) {
                    log.error("Пользователь с таким email {} уже существует ", userDto.getEmail());
                    throw new AlreadyExistException("Пользователь с таким email уже существует");
                }
            }
            return userRepository.updateUser(userDto, id);
        } else
            log.info("Пользователь с id {} не найден", id);
        throw new ObjectNotFoundException("Пользователь не найден");
    }

    public void deleteUser(Long id) throws ObjectNotFoundException {
        userRepository.deleteUser(id);
    }

    public Collection<UserDto> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public UserDto getUserById(Long id) throws ObjectNotFoundException {
        if (!userRepository.getUsers().containsKey(id)) {
            log.info("Пользователь с id {} не найден", id);
            throw new ObjectNotFoundException("Пользователь с id " + id + " не найден");
        }
        return userRepository.getUserById(id);
    }
}
