package ru.vovai.naumentesttask.model;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Name {
    private String name;
    private int age;
}
