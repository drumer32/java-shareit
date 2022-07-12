package ru.practicum.shareit.user.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.ObjectNotFoundException;
import ru.practicum.shareit.exceptions.ObjectNotValidException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository {
    private static long id = 0;
    private final Map<Long, User> users;

    public UserDto createUser(UserDto userDto) throws ObjectNotValidException {
        if (userDto.getEmail() == null) {
            log.warn("Пользователь не создан, email не может быть пустым. ");
            throw new ObjectNotValidException("Пользователь не создан, email не может быть пустым");
        } else {
            Long id = nextId();
            userDto.setId(id);
            users.put(id, UserMapper.toUser(userDto));
            log.info("Пользователь создан, id пользователя {} ", id);
            return userDto;
        }
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long id) {
        User newUser = users.get(id);
        if (userDto.getEmail() != null) {
            newUser.setEmail(userDto.getEmail());
        }
        if (userDto.getName() != null) {
            newUser.setName(userDto.getName());
        }
        log.info("Пользователь c id {} обновлён", newUser.getId());
        return UserMapper.userToDto(newUser);
    }

    @Override
    public void deleteUser(Long id) throws ObjectNotFoundException {
        if (users.containsKey(id)) {
            users.remove(id);
        } else throw new ObjectNotFoundException("Пользователь не найден");
    }

    @Override
    public UserDto getUserById(Long id) {
        log.info("Пользователь c id {} получен", id);
        return UserMapper.userToDto(users.get(id));
    }

    @Override
    public Map<Long, User> getUsers() {
        return users;
    }

    @Override
    public Collection<UserDto> getAllUsers() {
        Collection<UserDto> usersDto = new ArrayList<>();
        users.values().forEach(user -> usersDto.add(UserMapper.userToDto(user)));
        log.info("Список всех пользователей получен");
        return usersDto;
    }

    private Long nextId() {
        return ++id;
    }
}
