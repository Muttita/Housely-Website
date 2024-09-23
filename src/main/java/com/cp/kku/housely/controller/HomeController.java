package com.cp.kku.housely.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.cp.kku.housely.service.CategoryService;
import com.cp.kku.housely.service.ProductService;

public class HomeController {
    @Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;


}
