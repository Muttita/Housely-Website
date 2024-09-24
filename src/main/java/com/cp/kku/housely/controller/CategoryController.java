package com.cp.kku.housely.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cp.kku.housely.model.Category;
import com.cp.kku.housely.service.CategoryService;

import java.util.List;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String listCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories().collectList().block();
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
        categoryService.createCategory(category).block();
        return "redirect:/admin/categories"; // Redirect to the category list after saving
    }

    @GetMapping("/edit/{id}")
    public String showEditCategoryForm(@PathVariable Long id, Model model) {
        Category category = categoryService.getCategoryById(id).block();
        model.addAttribute("category", category);
        return "edit-category-form"; // Return the template for editing a category
    }
    
    @PostMapping("/save/{id}")
    public String updateCategory(@ModelAttribute("category") Category category, @PathVariable Long id) {
    	category.setCategoryId(id);
        categoryService.createCategory(category).block();
        return "redirect:/admin/categories"; // Redirect to the category list after saving
    }
    

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            categoryService.deleteCategory(id).block();
            redirectAttributes.addFlashAttribute("message", "Category deleted successfully.");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Cannot delete category. Reason: " + e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }
        return "redirect:/admin/categories";
    }
    
}
