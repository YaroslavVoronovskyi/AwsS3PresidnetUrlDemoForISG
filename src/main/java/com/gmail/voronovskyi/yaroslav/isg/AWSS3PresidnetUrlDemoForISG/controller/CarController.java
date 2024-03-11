package com.gmail.voronovskyi.yaroslav.isg.AWSS3PresidnetUrlDemoForISG.controller;

import com.gmail.voronovskyi.yaroslav.isg.AWSS3PresidnetUrlDemoForISG.dto.CarDto;
import com.gmail.voronovskyi.yaroslav.isg.AWSS3PresidnetUrlDemoForISG.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("/cars/{id}")
    private CarDto get(@PathVariable int id) {
        return carService.get(id);
    }
}
