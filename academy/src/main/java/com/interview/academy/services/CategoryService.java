package com.interview.academy.services;

import com.interview.academy.domain.entities.Category;
import jakarta.transaction.Transactional;

import java.util.List;

public interface CategoryService {
    List<Category> listCategories();
    Category createCategory(Category category);
}
