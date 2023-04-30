package ru.vovai.naumentesttask.service;

import ru.vovai.naumentesttask.dto.NameDTO;
import ru.vovai.naumentesttask.dto.StatisticDTO;

import java.io.IOException;
import java.util.List;

public interface NameAgeService {
    NameDTO getAge(String name) throws IOException;
    NameDTO getNameWithMaxAge() throws IOException;
    List<StatisticDTO> getStatistics() throws IOException;
}
