package com.exam.services;

import com.exam.entity.Category;

import java.util.Set;

public interface CategoryService {
    public Category addCategory(Category category);
    public Category updateCategory(Category category);
    public Set<Category> getCategories();
    public Category getCategory(Long categoryId);
    public void delete(Long categoryId);
}
