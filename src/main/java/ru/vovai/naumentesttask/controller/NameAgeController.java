package ru.vovai.naumentesttask.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.vovai.naumentesttask.dto.NameDTO;
import ru.vovai.naumentesttask.dto.StatisticDTO;
import ru.vovai.naumentesttask.service.NameAgeService;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/name")
@RequiredArgsConstructor
public class NameAgeController {

    private final NameAgeService nameAgeService;
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public NameDTO getAge(@RequestParam(value = "name") String name) throws IOException {
        return nameAgeService.getAge(name);
    }

    @GetMapping("/maxAge")
    @ResponseStatus(HttpStatus.OK)
    public NameDTO getMaxAge() throws IOException {
        return nameAgeService.getNameWithMaxAge();
    }

    @GetMapping("/statistics")
    @ResponseStatus(HttpStatus.OK)
    public List<StatisticDTO> getStatistics() throws IOException {
        return nameAgeService.getStatistics();
    }

}
