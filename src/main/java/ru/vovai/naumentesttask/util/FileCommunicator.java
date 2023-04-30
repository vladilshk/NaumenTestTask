package ru.vovai.naumentesttask.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileCommunicator {
    List<String> readAllDataFromFile(File file) throws IOException;

    void writeDataTOFile(List<String> data, File file);

    String getStrWithName(String name, File file);

    void writeLine(String line, File file) throws IOException;
}
