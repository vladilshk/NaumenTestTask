package ru.vovai.naumentesttask.util.impl;

import org.springframework.stereotype.Component;
import ru.vovai.naumentesttask.util.FileCommunicator;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class FileCommunicatorImpl implements FileCommunicator {
    @Override
    public List<String> readAllDataFromFile(String uri) {
        try {
            Path path = Paths.get(uri);
            Scanner scanner = new Scanner(path);
            List<String> data = new ArrayList<>();
            while (scanner.hasNextLine()){
                data.add(scanner.nextLine().replace("\n", ""));
            }
            return data;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeDataTOFile(List<String> data, String uri) {
        try {
            FileWriter fileWriter = new FileWriter(uri);
            for (int i = 0; i < data.size(); i++) {
                if (i != 0){
                    fileWriter.write("\n");
                }
                fileWriter.write(data.get(i));
            }
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getStrWithName(String name, String uri) {
        try {
            Path path = Paths.get(uri);
            Scanner scanner = new Scanner(path);
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                if (line.startsWith(name + "_")){
                    return line.replace("\n", "");
                }
            }
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeLine(String line, String uri) throws IOException {
        Path path = Paths.get(uri);
        Scanner scanner = new Scanner(path);
        FileWriter fileWriter = new FileWriter(uri, true);
        if (scanner.hasNextLine()){
            fileWriter.write("\n" + line);
        } else {
            fileWriter.write(line);
        }
        fileWriter.close();
    }
}
