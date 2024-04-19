package com.exam.enums;

import lombok.Getter;

@Getter
public enum EQuestionType {
    MULTIPLE_CHOICE("multiple_choice", "Câu hỏi nhiều lựa chọn"),
    SINGLE_CHOICE("single_choice", "Câu hỏi một lựa chọn");

    EQuestionType(String alias, String displayName) {
        this.alias = alias;
        this.displayName = displayName;
    }

    private final String alias;
    private final String displayName;

    public static EQuestionType getByAlias(String alias) {
        for (EQuestionType type : values()) {
            if (type.getAlias().equalsIgnoreCase(alias)) {
                return type;
            }
        }
        return null; // Trả về null nếu không tìm thấy alias
    }
}
