package com.cp.kku.demo.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cp.kku.demo.model.Category;
import com.cp.kku.demo.model.Product;
import com.cp.kku.demo.model.Room;
import com.cp.kku.demo.service.CategoryService;
import com.cp.kku.demo.service.ProductService;
import com.cp.kku.demo.service.RoomService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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
        model.addAttribute("products", productService.getAllProducts());
        return "product-list";
    }

    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("rooms", roomService.getAllRooms());

        return "add-product-form";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product, @RequestParam("image") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                // สร้างชื่อไฟล์ใหม่
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path path = Paths.get("src/main/resources/static/uploads/" + fileName);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                
                // บันทึกเส้นทางไฟล์ในฐานข้อมูล
                product.setImageBase64(fileName); // ใช้ field ใหม่สำหรับเก็บเส้นทางไฟล์
            } catch (IOException e) {
                e.printStackTrace();
                return "error"; // จัดการข้อผิดพลาด
            }
        }

        productService.saveProduct(product);
        
        try {
            // หยุดการทำงานเป็นเวลา 2 วินาที (2000 มิลลิวินาที)
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("rooms", roomService.getAllRooms());
        return "edit-product-form";
    }
    
    @PostMapping("/save/{id}")
    public String updateProduct(@ModelAttribute("product") Product product, @PathVariable Long id, @RequestParam("image") MultipartFile file) {
        product.setId(id);
        if (!file.isEmpty()) {
            try {
                // สร้างชื่อไฟล์ใหม่
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path path = Paths.get("src/main/resources/static/uploads/" + fileName);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                
                // บันทึกเส้นทางไฟล์ในผลิตภัณฑ์
                product.setImageBase64(fileName); // เปลี่ยนให้เป็นฟิลด์สำหรับเก็บเส้นทางไฟล์
            } catch (IOException e) {
                e.printStackTrace();
                return "error"; // จัดการข้อผิดพลาด
            }
        }    
        
        productService.saveProduct(product);
        
        try {
            // หยุดการทำงานเป็นเวลา 2 วินาที (2000 มิลลิวินาที)
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "redirect:/products"; 
    }


    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}

