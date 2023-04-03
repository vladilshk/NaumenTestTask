package ru.vovai.naumentesttask.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NameDTO {
    private String name;
    private int age;
    private int views;
}
