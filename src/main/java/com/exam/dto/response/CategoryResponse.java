package com.exam.dto.response;

import com.exam.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private Long id;
    private String title;
    private String description;

    public CategoryResponse(Category category) {
        this.id = category.getId();
        this.title = category.getTitle();
        this.description = category.getDescription();
    }
}
