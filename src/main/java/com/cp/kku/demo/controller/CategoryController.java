package com.cp.kku.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.cp.kku.demo.model.Category;
import com.cp.kku.demo.service.CategoryService;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String listCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "category-list"; // Return the template for displaying categories
    }

    @GetMapping("/add")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "add-category-form"; // Return the template for adding a category
    }

    @PostMapping("/save")
    public String saveCategory(@ModelAttribute("category") Category category) {
        categoryService.saveCategory(category);
        return "redirect:/categories"; // Redirect to the category list after saving
    }

    @GetMapping("/edit/{id}")
    public String showEditCategoryForm(@PathVariable Long id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "edit-category-form"; // Return the template for editing a category
    }
    
    @PostMapping("/save/{id}")
    public String updateCategory(@ModelAttribute("category") Category category, @PathVariable Long id) {
    	category.setCategoryId(id);
        categoryService.saveCategory(category);
        return "redirect:/categories"; // Redirect to the category list after saving
    }
    

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/categories"; // Redirect to the category list after deletion
    }
}
