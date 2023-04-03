package ru.vovai.naumentesttask.repository.impl;

import jdk.jfr.Registered;
import org.springframework.stereotype.Repository;
import ru.vovai.naumentesttask.model.Statistic;
import ru.vovai.naumentesttask.repository.StatisticsRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

@Repository
public class StatisticsRepositoryImpl implements StatisticsRepository {

    private final String dataUri = "src\\main\\resources\\static\\statistics.txt";


    @Override
    public Statistic save(Statistic statistic) throws IOException {
        FileWriter fileWriter = new FileWriter(dataUri, true);
        fileWriter.write(statistic.getName() + "_" + statistic.getCount() + "\n");
        fileWriter.close();
        return null;
    }



    @Override
    public Optional<Statistic> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<Statistic> findById(Long id) {
        return Optional.empty();
    }
}
