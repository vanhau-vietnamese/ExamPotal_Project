package com.exam.service;

import com.exam.dto.request.StatisticRequest;
import org.springframework.http.ResponseEntity;

public interface InformationService {
    public ResponseEntity<?> statistics(StatisticRequest statisticRequest);

    ResponseEntity<?> quantityStatistics();
}
