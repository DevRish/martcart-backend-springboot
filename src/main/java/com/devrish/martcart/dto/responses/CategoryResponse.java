package com.devrish.martcart.dto.responses;

import com.devrish.martcart.model.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryResponse extends GenericResponse {

    private Category category;
    private List<Category> categories;

    public CategoryResponse(Boolean status, String message, Category category, List<Category> categories) {
        super(status, message);
        this.category = category;
        this.categories = categories;
    }
}
