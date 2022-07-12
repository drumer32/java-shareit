package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exceptions.ObjectNotFoundException;
import ru.practicum.shareit.exceptions.ObjectNotValidException;
import ru.practicum.shareit.item.model.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private static final String HEADER_REQUEST = "X-Sharer-User-Id";
    private final ItemService itemService;

    @PostMapping
    public ItemDto createItem(@Valid @RequestBody ItemDto itemDto, @RequestHeader(HEADER_REQUEST) Long userId)
            throws ObjectNotValidException, ObjectNotFoundException {
        return itemService.createItem(itemDto, userId);
    }

    @PatchMapping(value = {"/{id}"})
    public ItemDto updateItem(@Validated @PathVariable Long id,
                              @RequestBody ItemDto itemDto,
                              @RequestHeader(HEADER_REQUEST) Long userId)
            throws ObjectNotFoundException, ObjectNotValidException {
        return itemService.updateItem(id, itemDto, userId);
    }

    @GetMapping(value = {"/{id}"})
    public ItemDto getItemById(@PathVariable Long id) throws ObjectNotFoundException {
        return itemService.getItemById(id);
    }

    @GetMapping("/search")
    public Collection<ItemDto> searchItemByTitle(@RequestParam String text) {
        return itemService.searchItemByTitle(text);
    }

    @DeleteMapping(value = {"/{id}"})
    public void deleteItem(@PathVariable Long id) throws ObjectNotFoundException {
        itemService.deleteItem(id);
    }

    @GetMapping
    public Collection<ItemDto> getItemByUserId(@RequestHeader(HEADER_REQUEST) Long userId)
            throws ObjectNotFoundException {
        return itemService.getItemsByUserId(userId);
    }
}
