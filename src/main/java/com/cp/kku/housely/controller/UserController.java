package com.cp.kku.housely.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.cp.kku.housely.service.ProductService;
import com.cp.kku.housely.service.CategoryService;
import com.cp.kku.housely.service.RoomService;
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
        model.addAttribute("products", productService.getAllProducts().collectList().block());
        model.addAttribute("categorys", categoryService.getAllCategories().collectList().block());
        model.addAttribute("rooms", roomService.getAllRooms().collectList().block());
        return "user-product";
    }

    @GetMapping("/product/{id}")
    public String getViewProductById(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id).block());
        return "user-product-detail";
    }

    @GetMapping("/product/category/{id}")
    public String getViewProductByCategory(@PathVariable Long id, Model model) {
        model.addAttribute("products", productService.getProductsByCategoryId(id).collectList().block());
        model.addAttribute("categorys", categoryService.getAllCategories().collectList().block());
        model.addAttribute("rooms", roomService.getAllRooms().collectList().block());

        return "user-product";
    }

    @GetMapping("/product/room/{id}")
    public String getViewProductByRoom(@PathVariable Long id, Model model) {
        model.addAttribute("products", productService.getProductsByRoomId(id).collectList().block());
        model.addAttribute("categorys", categoryService.getAllCategories().collectList().block());
        model.addAttribute("rooms", roomService.getAllRooms().collectList().block());
        
        return "user-product";
    }



}
