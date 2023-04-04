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
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NameAgeService {


    private final NameAgeRepository nameAgeRepository;
    private final StatisticsRepository statisticsRepository;
    private final RestTemplate restTemplate;
    //Name - id
    //TODO: add cache , maybe replace hashMap with radis
    private final HashMap<String , Integer> namesCache;

    public NameDTO getAge(String name) throws IOException {
        Optional<Name> answerName = nameAgeRepository.findByName(name);

        if (answerName.isPresent()){
            if (statisticsRepository.findByName(name).isPresent()) {
                statisticsRepository.update(name);
            } else{
                statisticsRepository.save(Statistic.builder()
                                .name(name)
                                .count(1)
                        .build());
            }
            return convertNameToDTO(answerName.get());
        } else {
            System.out.println("blia");
            return convertNameToDTO(addName(name));
        }
    }

    public List<StatisticDTO> getAllNames() throws IOException {
        List<Name> nameList = nameAgeRepository.findAll();
        return nameList.stream().map(name -> {
            Statistic statistic = statisticsRepository.findByName(name.getName()).orElse(null);
            try {
                return createStatisticDTO(name, statistic);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }

    public NameDTO getMaxAge() throws IOException {
        List<Name> nameList = nameAgeRepository.findAll();
        if (nameList == null || nameList.size() == 0){
            return null;
        }
        Name nameWithMaxAge = nameList.get(0);
        for (int i = 0; i < nameList.size(); i++) {
            Name currentName = nameList.get(i);
            if (nameWithMaxAge.getAge() < currentName.getAge()){
                nameWithMaxAge = currentName;
            }
        }
        return convertNameToDTO(nameWithMaxAge);
    }

    public Name addName(String name) throws IOException {
        String url = "https://api.agify.io/?name=" + name;
        NameRequest nameRequest = restTemplate.getForObject(url, NameRequest.class);
        //TODO: if null;;
        //TODO: addCounter
        //TODO: addTransactional
        statisticsRepository.save(Statistic.builder()
                        .name(nameRequest.getName())
                        .count(nameRequest.getCount())
                .build());
        return nameAgeRepository.save(convertNameRequestToName(nameRequest));
    }

    public Name convertNameRequestToName(NameRequest nameRequest){
        return Name.builder()
                .name(nameRequest.getName())
                .age(nameRequest.getAge())
                .build();
    }

    private NameDTO convertNameToDTO(Name name){
        return NameDTO.builder()
                .name(name.getName())
                .age(name.getAge())
                .build();
    }

    public StatisticDTO createStatisticDTO(Name name, Statistic statistic) throws IOException {
        if (statistic == null){
            statistic = statisticsRepository.save(
                    Statistic.builder()
                            .name(name.getName())
                            .count(0)
                            .build()
            );
        }
        return StatisticDTO.builder()
                .name(name.getName())
                .age(name.getAge())
                .count(statistic.getCount())
                .build();
    }
}
