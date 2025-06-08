package com.exam.mapper;

import com.exam.chatbot.dto.AnswerDto;
import com.exam.helper.AnswerObject;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
    AnswerDto toAnswerDto(AnswerObject answerObject);
}
