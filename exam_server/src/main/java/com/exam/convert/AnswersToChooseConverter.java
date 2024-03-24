package com.exam.convert;

import com.exam.model.AnswersToChoose;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;

public class AnswersToChooseConverter implements AttributeConverter<AnswersToChoose, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public String convertToDatabaseColumn(AnswersToChoose answersToChoose) {
        try {
            return objectMapper.writeValueAsString(answersToChoose);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting AnswersToChoose to JSON", e);
        }
    }

    @Override
    public AnswersToChoose convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, AnswersToChoose.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting JSON to AnswersToChoose", e);
        }
    }
}
