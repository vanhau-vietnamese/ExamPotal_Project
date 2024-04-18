//package com.exam.convert;
//
//import com.exam.helper.ExamObject;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.JavaType;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.persistence.AttributeConverter;
//import jakarta.persistence.Convert;
//
//import java.io.IOException;
//
//@Convert
//public class JsonConverter<T> implements AttributeConverter<T, String> {
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Override
//    public String convertToDatabaseColumn(T object) {
//        String objInfoJson = null;
//        try {
//            objInfoJson = objectMapper.writeValueAsString(object);
//        } catch (Exception e) {
//            throw new IllegalArgumentException("Error converting to JSON", e);
//        }
//        return objInfoJson;
//    }
//
//    @Override
//    public T convertToEntityAttribute(String dbData) {
//        T dbDataInfo = null;
//        try {
////            // Sử dụng TypeReference để chỉ định kiểu dữ liệu cụ thể
////            TypeReference<T> typeReference = new TypeReference<>() {};
////            dbDataInfo = objectMapper.readValue(dbData,typeReference);
//            // Sử dụng TypeFactory để tạo JavaType từ kiểu dữ liệu cụ thể
//            dbDataInfo = objectMapper.readValue(dbData, new TypeReference<T>() {});
//        } catch (IOException e) {
//            throw new IllegalArgumentException("Error converting JSON", e);
//        }
//        return dbDataInfo;
//    }
//}
package com.exam.convert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;

public interface JsonConverter<T> extends AttributeConverter<T, String> {
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    default String convertToDatabaseColumn(T object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting to JSON", e);
        }
    }

    @Override
    default T convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, getType());
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting JSON", e);
        }
    }

    Class<T> getType();
}
