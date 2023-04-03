package ru.vovai.naumentesttask.repository;

import ru.vovai.naumentesttask.model.Name;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface NameAgeRepository {
    Optional<Integer> getAge(String name);

    Optional<Name> findByName(String name);

    List<Name> findAll() throws IOException;

    Name save(Name name);
}
