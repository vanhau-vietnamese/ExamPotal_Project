package com.exam.dto.request;

import com.exam.model.Quiz;
import com.exam.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

public class CategoryRequest {
    private String title;
    private String description;
    private Long createByUserId;
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

}
