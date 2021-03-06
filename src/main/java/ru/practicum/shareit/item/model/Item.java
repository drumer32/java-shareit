package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Data
@Builder
public class Item {

    private Long id;
    private String name;
    private String description;
    private boolean available;
    @NotNull
    private Long owner;
    private String request;
}
