package com.devrish.martcart.service;

import com.devrish.martcart.dto.responses.CategoryResponse;
import com.devrish.martcart.exception.category.CategoryNotFoundException;
import com.devrish.martcart.model.Category;
import com.devrish.martcart.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryResponse getAll() throws Exception {
        List<Category> categories = categoryRepository.findAll();
        return new CategoryResponse(
                true,
                "Categories fetched successfully",
                null,
                categories
        );
    }

    public CategoryResponse getById(String id) throws Exception {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) throw new CategoryNotFoundException();
        return new CategoryResponse(
                true,
                "Category fetched successfully",
                category,
                null
        );
    }

    public CategoryResponse createCategory(Category category) throws Exception {
        categoryRepository.save(category);
        return new CategoryResponse(
                true,
                "Successfully created new category with id: " + category.get_id(),
                category,
                null
        );
    }

}
