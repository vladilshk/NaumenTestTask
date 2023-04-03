package ru.vovai.naumentesttask.repository.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.vovai.naumentesttask.model.Name;
import ru.vovai.naumentesttask.repository.NameAgeRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/*@Component*/
@Repository
public class NameAgeRepositoryImpl implements NameAgeRepository {

    /*@Value("${name.data}")*/
    private final String dataUri = "src\\main\\resources\\static\\names.txt";
    /*private final Path path;*/

    @Override
    public Optional<Integer> getAge(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<Name> findByName(String name){
        try {
            Path path = Paths.get(dataUri);
            Scanner scanner = new Scanner(path);
            while (scanner.hasNextLine()) {
                Name currentName = convertStringToName(scanner.nextLine());
                if (currentName.getName().equals(name)) {
                    return Optional.of(currentName);
                }
            }
        } catch (Exception e){
            //TODO:
        }
        return Optional.empty();
    }

    public List<Name> findAll() throws IOException {
        List<Name> nameList = new ArrayList<>();
        Path path = Paths.get(dataUri);
        Scanner scanner = new Scanner(path);
        while (scanner.hasNextLine()){
            nameList.add(convertStringToName(scanner.nextLine()));
        }
        return nameList;
    }

    @Override
    public Name save(Name name) {
        try {
            FileWriter fileWriter = new FileWriter(dataUri, true);
            fileWriter.write("\n" + name.getName() + "_" + name.getAge());
            fileWriter.close();
            return name;
        } catch (IOException e) {
            //TODO: change exception
            throw new RuntimeException(e);
        }
    }

    public Name convertStringToName(String nameData){
        System.out.println(nameData);
        //TODO: add exception
        String[] nameAndAge = nameData.split("_");
        return new Name(nameAndAge[0], Integer.parseInt(nameAndAge[1]));
    }
}
