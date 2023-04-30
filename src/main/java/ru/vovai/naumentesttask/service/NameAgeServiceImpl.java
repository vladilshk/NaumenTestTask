package ru.vovai.naumentesttask.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.vovai.naumentesttask.dto.NameDTO;
import ru.vovai.naumentesttask.dto.NameRequest;
import ru.vovai.naumentesttask.dto.StatisticDTO;
import ru.vovai.naumentesttask.model.Name;
import ru.vovai.naumentesttask.model.Statistic;
import ru.vovai.naumentesttask.repository.NameAgeRepository;
import ru.vovai.naumentesttask.repository.StatisticsRepository;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class NameAgeServiceImpl implements NameAgeService {


    private final NameAgeRepository nameAgeRepository;
    private final StatisticsRepository statisticsRepository;
    private final RestTemplate restTemplate;
    //Name - age
    //TODO: add cache , maybe replace hashMap with radis
    private final HashMap<String, Integer> namesCache;

    @Override
    public NameDTO getAge(String name) throws IOException {
        Name nameData;
        if (namesCache.containsKey(name)) {
            nameData = Name.builder()
                    .name(name)
                    .age(namesCache.get(name))
                    .build();
            addStatistics(nameData);
        } else {
            Optional<Name> answerName = nameAgeRepository.findByName(name);
            if (answerName.isPresent()) {
                nameData = answerName.get();
                addStatistics(nameData);
            } else {
                nameData = addName(name);
            }
            namesCache.put(name, nameData.getAge());
        }
        return convertNameToDTO(nameData);
    }

    @Override
    public NameDTO getNameWithMaxAge() throws IOException {
        List<Name> nameList = nameAgeRepository.findAll();
        if (nameList == null || nameList.size() == 0) {
            return null;
        }
        Name nameWithMaxAge = nameList.get(0);
        for (int i = 0; i < nameList.size(); i++) {
            Name currentName = nameList.get(i);
            if (nameWithMaxAge.getAge() < currentName.getAge()) {
                nameWithMaxAge = currentName;
            }
        }
        return convertNameToDTO(nameWithMaxAge);
    }

    public Name addName(String name) throws IOException {
        String url = "https://api.agify.io/?name=" + name;
        NameRequest nameRequest = restTemplate.getForObject(url, NameRequest.class);
        if (nameRequest == null) {
            throw new IOException("Can't find the name");
        }
        if (nameRequest.getCount() == 0) {
            nameRequest.setCount(1);
        }
        statisticsRepository.save(Statistic.builder()
                .name(nameRequest.getName())
                .count(nameRequest.getCount())
                .build());
        return nameAgeRepository.save(convertNameRequestToName(nameRequest));
    }

    public void addStatistics(Name nameFromDb) throws IOException {
        String name = nameFromDb.getName();
        if (statisticsRepository.findByName(name).isPresent()) {
            statisticsRepository.update(name);
        } else {
            statisticsRepository.save(Statistic.builder()
                    .name(name)
                    .count(1)
                    .build());
        }
    }

    @Override
    public List<StatisticDTO> getStatistics() throws IOException {
        List<StatisticDTO> statisticDTOList = new ArrayList<>(statisticsRepository.findAll()
                .stream().map(this::convertStatisticToDTO)
                .toList());
        statisticDTOList.sort(new Comparator<StatisticDTO>() {
            @Override
            public int compare(StatisticDTO o1, StatisticDTO o2) {
                if (o1.getCount() < o2.getCount()) {
                    return 1;
                } else if (o1.getCount() > o2.getCount()) {
                    return -1;
                }
                return 0;
            }
        });
        return statisticDTOList;
    }

    public StatisticDTO convertStatisticToDTO(Statistic statistic) {
        return StatisticDTO.builder()
                .name(statistic.getName())
                .count(statistic.getCount())
                .build();
    }

    public Name convertNameRequestToName(NameRequest nameRequest) {
        return Name.builder()
                .name(nameRequest.getName())
                .age(nameRequest.getAge())
                .build();
    }

    private NameDTO convertNameToDTO(Name name) {
        return NameDTO.builder()
                .name(name.getName())
                .age(name.getAge())
                .build();
    }
}
