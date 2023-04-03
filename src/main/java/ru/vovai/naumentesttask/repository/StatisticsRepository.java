package ru.vovai.naumentesttask.repository;

import ru.vovai.naumentesttask.model.Statistic;

import java.io.IOException;
import java.util.Optional;

public interface StatisticsRepository {
    Statistic save(Statistic statistic) throws IOException;

    Optional<Statistic> findByName(String name);

    Optional<Statistic> findById(Long id);
}
