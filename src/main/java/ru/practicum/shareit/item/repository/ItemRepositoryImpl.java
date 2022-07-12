package ru.practicum.shareit.item.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.ObjectNotFoundException;
import ru.practicum.shareit.item.model.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ItemRepositoryImpl implements ItemRepository {

    private static long id = 0;
    private final Map<Long, Item> items;


    @Override
    public ItemDto createItem(ItemDto itemDto, Long userId) {
        Long id = generateId();
        itemDto.setId(id);
        Item item = ItemMapper.toItem(itemDto);
        item.setOwner(userId);
        items.put(id, item);
        log.info("Вещь создана, id вещи {} ", id);
        return itemDto;
    }

    @Override
    public ItemDto updateItem(Long itemId, ItemDto itemDto, Long id) throws ObjectNotFoundException {
        Item newItem;
        if (items.get(itemId) == null) {
            throw new ObjectNotFoundException("Невозможно обновить данные о вещи. Сперва создайте ее.");
        } else newItem = items.get(itemId);
        if (newItem.getOwner().equals(id)) {
            if (itemDto.getName() != null) {
                newItem.setName(itemDto.getName());
            }
            if (itemDto.getDescription() != null) {
                newItem.setDescription(itemDto.getDescription());
            }
            if (itemDto.getAvailable() != null) {
                newItem.setAvailable(itemDto.getAvailable());
            }
            log.info("Вещь c id {} обновлена", itemId);
            return ItemMapper.itemToDto(newItem);
        } else
            log.info("Пользователь {} не владеет вещью {}",id, itemId);
            throw new ObjectNotFoundException("Пользователь не владеет вещью!");
    }

    @Override
    public Collection<ItemDto> searchItemByTitle(String text) {
        if (Objects.equals(text, "")) {
            log.info("Text is null");
            return new ArrayList<>();
        }
        ItemDto itemDto;
        Collection<ItemDto> itemsByTitle = new ArrayList<>();
        for (Item item : items.values()) {
            if (item.getName().toLowerCase().contains(text.toLowerCase()) && item.isAvailable() ||
                    item.getDescription().toLowerCase().contains(text.toLowerCase()) && item.isAvailable()) {
                itemDto = ItemMapper.itemToDto(item);
                log.info("Search by title: {}, {}", text, itemDto.getId() + " " + itemDto.getName());
                itemsByTitle.add(itemDto);
            }
        }
        return itemsByTitle;
    }

    @Override
    public void deleteItem(Long id) throws ObjectNotFoundException {
        if (items.containsKey(id)) {
        items.remove(id);
        log.info("Вещь {} удалена", id);
        } else
            throw new ObjectNotFoundException("Вещь не найдена");
    }

    public Collection<ItemDto> getItemsByUserId(Long id) {
        Collection<ItemDto> itemsOfUser = new ArrayList<>();
        items.values().forEach(item -> {
            if (item.getOwner().equals(id)) {
                itemsOfUser.add(ItemMapper.itemToDto(item));
            }
        });
        log.info("Показаны вещи пользователя {}", id);
        return itemsOfUser;
    }

    @Override
    public ItemDto getItemById(Long id) throws ObjectNotFoundException {
        if (items.containsKey(id)) {
            log.info("Вещь {} найдена", id);
            return ItemMapper.itemToDto(items.get(id));
        } else
            throw new ObjectNotFoundException("Вещь не существует");
    }

    private Long generateId() {
        return ++id;
    }
}
