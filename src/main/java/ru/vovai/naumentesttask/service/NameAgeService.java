package ru.vovai.naumentesttask.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.vovai.naumentesttask.dto.NameDTO;
import ru.vovai.naumentesttask.dto.NameRequest;
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
    private final HashMap<String , Integer> namesCache;

    public NameDTO getAge(String name) throws IOException {
        Optional<Name> answerName = nameAgeRepository.findByName(name);
        //if db contains this name, we return age, else we add this name to db with random age
        /*if (answerName.isPresent()){
            return convertNameToDTO(answerName.get());
        }
        else
            return null;*/

       /* statisticsRepository.save(*//*(statisticsRepository.findByName(name).orElse(*//*
                    Statistic.builder()
                        .name(name)
                        .count(1)
                        .build()
        *//*)*//*);*/
        return convertNameToDTO(answerName.orElseGet(() -> {
            try {
                return addName(name);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    public List<NameDTO> getAllNames() throws IOException {
        List<Name> nameList = nameAgeRepository.findAll();
        return nameList.stream().map(this::convertNameToDTO).toList();
    }

    private NameDTO convertNameToDTO(Name name){
        return NameDTO.builder()
                .name(name.getName())
                .age(name.getAge())
                .build();
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
}
