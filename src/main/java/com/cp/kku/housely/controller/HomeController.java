package com.cp.kku.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.cp.kku.demo.service.CartService;
import com.cp.kku.demo.service.CategoryService;
import com.cp.kku.demo.service.ProductService;

public class HomeController {
    @Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;

	@Autowired
	private CartService cartService;

}
