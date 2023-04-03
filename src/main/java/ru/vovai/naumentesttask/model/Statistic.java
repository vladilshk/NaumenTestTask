package ru.vovai.naumentesttask.model;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Statistic {
    private String name;
    private long count;
}
