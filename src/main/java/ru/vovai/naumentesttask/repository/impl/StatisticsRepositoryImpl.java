package ru.vovai.naumentesttask.repository.impl;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import ru.vovai.naumentesttask.model.Statistic;
import ru.vovai.naumentesttask.repository.StatisticsRepository;
import ru.vovai.naumentesttask.util.FileCommunicator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class StatisticsRepositoryImpl implements StatisticsRepository {

    private final String dataUri = "classpath:static/statistics.txt";
    private final File file;
    private final FileCommunicator fileCommunicator;

    public StatisticsRepositoryImpl(FileCommunicator fileCommunicator) throws FileNotFoundException {
        this.fileCommunicator = fileCommunicator;
        this.file =  ResourceUtils.getFile(dataUri);
    }


    @Override
    public Statistic save(Statistic statistic) throws IOException {
        fileCommunicator.writeLine(convertStatisticToLine(statistic), file);
        return statistic;
    }

    @Override
    public Optional<Statistic> findByName(String name) {
        String line = fileCommunicator.getStrWithName(name, file);
        if (line != null) {
            return Optional.of(convertStringTOStatistic(line));
        }
        return Optional.empty();
    }


    @SneakyThrows
    @Override
    public Optional<Statistic> update(String name){
        List<String> lines = fileCommunicator.readAllDataFromFile(file);
        List<String> updatedLines = new ArrayList<>();
        Statistic statistic = null;
        for (String line : lines){
            if (line.startsWith(name + "_")){
                statistic = convertStringTOStatistic(line);
                statistic.setCount(statistic.getCount() + 1);
                updatedLines.add(convertStatisticToLine(statistic));
            } else{
                updatedLines.add(line);
            }
        }
        fileCommunicator.writeDataTOFile(updatedLines, file);
        return Optional.of(statistic);
    }

    @Override
    public List<Statistic> findAll() throws IOException {
        return fileCommunicator.readAllDataFromFile(file)
                .stream().map(this::convertStringTOStatistic)
                .toList();
    }

    public Statistic convertStringTOStatistic(String str){
        String[] statisticData = str.split("_");
        return new Statistic(statisticData[0], Long.parseLong(statisticData[1]));
    }

    private String convertStatisticToLine(Statistic statistic){
        return statistic.getName() + "_" + statistic.getCount();
    }
}
