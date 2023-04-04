package ru.vovai.naumentesttask.util;

import java.io.IOException;
import java.util.List;

public interface FileCommunicator {
    List<String> readAllDataFromFile(String uri) throws IOException;

    void writeDataTOFile(List<String> data, String uri);

    String getStrWithName(String name, String uri);

    void writeLine(String line, String uri) throws IOException;
}
