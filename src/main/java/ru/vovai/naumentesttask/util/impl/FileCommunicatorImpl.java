package ru.vovai.naumentesttask.util.impl;

import org.springframework.stereotype.Component;
import ru.vovai.naumentesttask.util.FileCommunicator;

import java.io.File;
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
    public List<String> readAllDataFromFile(File file) throws IOException {
        try {
            Scanner scanner = new Scanner(file);
            List<String> data = new ArrayList<>();
            while (scanner.hasNextLine()){
                data.add(scanner.nextLine().replace("\n", ""));
            }
            return data;
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    @Override
    public void writeDataTOFile(List<String> data, File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);
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
    public String getStrWithName(String name, File file) {
        try {
            Scanner scanner = new Scanner(file);
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
    public void writeLine(String line, File file) throws IOException {
        Scanner scanner = new Scanner(file);
        FileWriter fileWriter = new FileWriter(file, true);
        if (scanner.hasNextLine()){
            fileWriter.write("\n" + line);
        } else {
            fileWriter.write(line);
        }
        fileWriter.close();
    }
}
