package ru.vovai.naumentesttask.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.vovai.naumentesttask.dto.NameDTO;
import ru.vovai.naumentesttask.service.NameAgeService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/name")
@RequiredArgsConstructor
public class NameAgeController {

    private final NameAgeService nameAgeService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public NameDTO getAge(@RequestParam(value = "name") String name) throws IOException {
        return nameAgeService.getAge(name);
    }

    @GetMapping("/statistics")
    @ResponseStatus(HttpStatus.OK)
    public List<NameDTO> getStatistics() throws IOException {
        return nameAgeService.getAllNames();
    }

    @GetMapping("/maxAge")
    @ResponseStatus(HttpStatus.OK)
    public NameDTO getMaxAge() throws IOException {
        return nameAgeService.getMaxAge();
    }

   /* @GetMapping("/test/")
    @ResponseStatus(HttpStatus.OK)
    public Name test(@RequestParam(value = "name") String name){
        System.out.println("Hello");

        return nameAgeService.getAge(name);
    }*/
}
