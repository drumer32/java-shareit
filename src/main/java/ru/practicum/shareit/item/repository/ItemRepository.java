package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.exceptions.ObjectNotFoundException;
import ru.practicum.shareit.exceptions.ObjectNotValidException;
import ru.practicum.shareit.item.model.ItemDto;

import java.util.Collection;

public interface ItemRepository {
    ItemDto createItem(ItemDto itemDto, Long userId) throws ObjectNotValidException;

    ItemDto updateItem(Long itemId, ItemDto itemDto, Long id) throws ObjectNotFoundException, ObjectNotValidException;

    void deleteItem(Long id) throws ObjectNotFoundException;

    Collection<ItemDto> searchItemByTitle(String text);

    Collection<ItemDto> getItemsByUserId(Long id) throws ObjectNotFoundException;

    ItemDto getItemById(Long id) throws ObjectNotFoundException;
}
