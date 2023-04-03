package ru.vovai.naumentesttask.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NameRequest {
    private String name;
    private int age;
    private long count;
}
