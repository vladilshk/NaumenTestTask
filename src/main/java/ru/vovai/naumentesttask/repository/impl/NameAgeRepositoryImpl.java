package ru.vovai.naumentesttask.repository.impl;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import ru.vovai.naumentesttask.model.Name;
import ru.vovai.naumentesttask.repository.NameAgeRepository;
import ru.vovai.naumentesttask.util.FileCommunicator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


@Component
public class NameAgeRepositoryImpl implements NameAgeRepository{

    private final String dataUri = "classpath:static/names.txt";

    private final File file;

    private final FileCommunicator fileCommunicator;

    public NameAgeRepositoryImpl(FileCommunicator fileCommunicator) throws FileNotFoundException {
        this.fileCommunicator = fileCommunicator;
        this.file = ResourceUtils.getFile(dataUri);
    }


    @Override
    public Optional<Name> findByName(String name){
        String line = fileCommunicator.getStrWithName(name, file);
        if (line != null){
            return Optional.of(convertStringToName(line));
        }
        return Optional.empty();
    }

    @Override
    public List<Name> findAll() throws IOException {
        return fileCommunicator.readAllDataFromFile(file)
                .stream().map(this::convertStringToName)
                .toList();
    }

    @Override
    public Name save(Name name) throws IOException {
        fileCommunicator.writeLine(convertNameToLine(name), file);
        return name;
    }

    public Name convertStringToName(String nameData){
        String[] nameAndAge = nameData.split("_");
        return new Name(nameAndAge[0], Integer.parseInt(nameAndAge[1]));
    }

    public String convertNameToLine(Name name){
        return name.getName() + "_" + name.getAge();
    }
}
