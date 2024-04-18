package com.exam.controller;

import com.exam.dto.request.StatisticRequest;
import com.exam.service.InformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class InformationController {
    private final InformationService informationService;
    @GetMapping("/statistics")
    public ResponseEntity<?> statistics(@RequestBody StatisticRequest statisticRequest){
        return informationService.statistics(statisticRequest);
    }
}
