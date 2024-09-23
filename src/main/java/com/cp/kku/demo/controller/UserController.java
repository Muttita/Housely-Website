package com.cp.kku.demo.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cp.kku.demo.model.Category;
import com.cp.kku.demo.model.Product;
import com.cp.kku.demo.service.CategoryService;
import com.cp.kku.demo.service.ProductService;
import com.cp.kku.demo.service.RoomService;

import io.micrometer.common.util.StringUtils;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RoomService roomService;

    @GetMapping("/product")
    public String showProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("categorys", categoryService.getAllCategories());
        model.addAttribute("rooms", roomService.getAllRooms());
        return "user-product";
    }

    @GetMapping("/product/{id}")
    public String getViewProductById(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "user-product-detail";
    }

    
   

}
