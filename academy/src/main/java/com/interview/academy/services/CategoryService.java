package com.interview.academy.services;

import com.interview.academy.domain.entities.Category;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<Category> listCategories();
    Category createCategory(Category category);
    void deleteCategory(UUID id);
}
