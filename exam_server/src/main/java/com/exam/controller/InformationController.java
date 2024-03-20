package com.exam.controller;

import com.exam.service.InformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class InformationController {
    private final InformationService informationService;
    @GetMapping("/statistics")
    public ResponseEntity<?> statistics(){
        return informationService.statistics();
    }
}
