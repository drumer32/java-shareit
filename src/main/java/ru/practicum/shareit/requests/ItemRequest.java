package ru.practicum.shareit.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class ItemRequest {
    Long id;
    String description;
    User requestor;
    LocalDateTime created;
}
