package ru.vovai.naumentesttask.repository.impl;

import jdk.jfr.Registered;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;
import ru.vovai.naumentesttask.model.Name;
import ru.vovai.naumentesttask.model.Statistic;
import ru.vovai.naumentesttask.repository.StatisticsRepository;
import ru.vovai.naumentesttask.util.FileCommunicator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Repository
@RequiredArgsConstructor
public class StatisticsRepositoryImpl implements StatisticsRepository {

    private final String dataUri = "src\\main\\resources\\static\\statistics.txt";
    private final FileCommunicator fileCommunicator;


    @Override
    public Statistic save(Statistic statistic) throws IOException {
        fileCommunicator.writeLine(convertStatisticToLine(statistic), dataUri);
        return statistic;
    }

    @Override
    public Optional<Statistic> findByName(String name) {
        String line = fileCommunicator.getStrWithName(name, dataUri);
        System.out.println(line);
        //TODO: can make it more beautiful
        if (line != null){
            return Optional.of(convertStringTOStatistic(line));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Statistic> findById(Long id) {
        return Optional.empty();
    }

    //TODO: find what does it means
    @SneakyThrows
    @Override
    public Optional<Statistic> update(String name){
        List<String> lines = fileCommunicator.readAllDataFromFile(dataUri);
        List<String> updatedLines = new ArrayList<>();
        Statistic statistic = null;
        for (String line : lines){
            if (line.startsWith(name + "_")){
                statistic = convertStringTOStatistic(line);
                statistic.setCount(statistic.getCount() + 1);
                //TODO: maybe we can replace it with method)
                updatedLines.add(convertStatisticToLine(statistic));
            } else{
                updatedLines.add(line);
            }
        }
        fileCommunicator.writeDataTOFile(updatedLines, dataUri);
        //if null
        return Optional.of(statistic);
    }

    public Statistic convertStringTOStatistic(String str){
        //TODO: add exciption
        String[] statisticData = str.split("_");
        return new Statistic(statisticData[0], Long.parseLong(statisticData[1]));
    }

    private String convertStatisticToLine(Statistic statistic){
        return statistic.getName() + "_" + statistic.getCount();
    }
}
