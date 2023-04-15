package com.example.poc.apps;

import com.example.poc.infra.repositories.TesteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ApiTest {

    private final TesteRepository testeRepository;

    @GetMapping("/testeRo")
    public String testeRo(){
        log.info("RO ".concat(testeRepository.getByRo().getName()));

        return "up";
    }

    @GetMapping("/testeRw")
    public String testeRw(){
        log.info("RW ".concat(testeRepository.getByRw().getName()));

        return "up";
    }


    @GetMapping("/teste")
    public String teste(){
        log.info("RO ".concat(testeRepository.getByRo().getName()));
        log.info("RW ".concat(testeRepository.getByRw().getName()));

        return "up";
    }

}
