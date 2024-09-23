package com.cp.kku.housely.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cp.kku.housely.model.Category;
import com.cp.kku.housely.model.Product;
import com.cp.kku.housely.model.Room;
import com.cp.kku.housely.service.CategoryService;
import com.cp.kku.housely.service.ProductService;
import com.cp.kku.housely.service.RoomService;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RoomService roomService;

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts().collectList().block());
        return "product-list";
    }

    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories().collectList().block());
        model.addAttribute("rooms", roomService.getAllRooms().collectList().block());

        return "add-product-form";
    }

@PostMapping("/save")
public String saveProduct(@ModelAttribute Product product, 
                          @RequestParam("categoryIds") List<Long> categoryIds,
                          @RequestParam("roomIds") List<Long> roomIds,
                          @RequestParam("image") MultipartFile file) {
    if (!file.isEmpty()) {
        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get("src/main/resources/static/uploads/" + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            product.setImageBase64(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

    // Convert category IDs to Category objects
    List<Category> categories = categoryIds.stream()
            .map(id -> {
                Category category = new Category();
                category.setCategoryId(id);
                return category;
            })
            .collect(Collectors.toList());
    product.setCategories(categories);

    // Convert room IDs to Room objects
    List<Room> rooms = roomIds.stream()
            .map(id -> {
                Room room = new Room();
                room.setId(id);
                return room;
            })
            .collect(Collectors.toList());
    product.setRooms(rooms);

    productService.createProduct(product).block();
    return "redirect:/products";
}

    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id).block());
        model.addAttribute("categories", categoryService.getAllCategories().collectList().block());
        model.addAttribute("rooms", roomService.getAllRooms().collectList().block());
        return "edit-product-form";
    }
    
    @PostMapping("/save/{Id}")
    public String updateProduct(@ModelAttribute Product product, 
                              @RequestParam("categoryIds") List<Long> categoryIds,
                              @RequestParam("roomIds") List<Long> roomIds,
                              @RequestParam("image") MultipartFile file,@PathVariable Long Id) {
        product.setId(Id);
        if (!file.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path path = Paths.get("src/main/resources/static/uploads/" + fileName);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                product.setImageBase64(fileName);
            } catch (IOException e) {
                e.printStackTrace();
                return "error";
            }
        }
    
        // Convert category IDs to Category objects
        List<Category> categories = categoryIds.stream()
                .map(id -> {
                    Category category = new Category();
                    category.setCategoryId(id);
                    return category;
                })
                .collect(Collectors.toList());
        product.setCategories(categories);
    
        // Convert room IDs to Room objects
        List<Room> rooms = roomIds.stream()
                .map(id -> {
                    Room room = new Room();
                    room.setId(id);
                    return room;
                })
                .collect(Collectors.toList());
        product.setRooms(rooms);
    
        productService.createProduct(product).block();
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id).block();
        return "redirect:/products";
    }
}

