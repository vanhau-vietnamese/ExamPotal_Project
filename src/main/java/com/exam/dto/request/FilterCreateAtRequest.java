package com.exam.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class FilterCreateAtRequest {
    private Timestamp fromTime;
    private Timestamp toTime;
}
