package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.ObjectNotFoundException;
import ru.practicum.shareit.exceptions.ObjectNotValidException;
import ru.practicum.shareit.item.model.ItemDto;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemService {

    private final UserService userService;
    private final ItemRepository itemRepository;

    public ItemDto createItem(ItemDto itemDto, Long userId) throws ObjectNotValidException, ObjectNotFoundException {
        userService.getUserById(userId);
        if (itemDto.getAvailable() == null || itemDto.getName() == null ||
                itemDto.getName().isEmpty() || itemDto.getDescription() == null) {
            log.warn("Не указано имя, описание товара или параметр доступности");
            throw new ObjectNotValidException("Не указано имя, описание товара или параметр доступности");
        } else return itemRepository.createItem(itemDto, userId);
    }

    public ItemDto updateItem(Long itemId, ItemDto itemDto, Long id) throws ObjectNotFoundException, ObjectNotValidException {
        return itemRepository.updateItem(itemId, itemDto, id);
    }

    public Collection<ItemDto> searchItemByTitle(String text) {
        return itemRepository.searchItemByTitle(text);
    }

    public void deleteItem(Long id) throws ObjectNotFoundException {
        itemRepository.deleteItem(id);
    }

    public Collection<ItemDto> getItemsByUserId(Long id) throws ObjectNotFoundException {
        if (userService.getUserById(id) != null) {
            return itemRepository.getItemsByUserId(id);
        } else
            throw new ObjectNotFoundException("Пользователь не найден!");
    }

    public ItemDto getItemById(Long id) throws ObjectNotFoundException {
        return itemRepository.getItemById(id);
    }
}
