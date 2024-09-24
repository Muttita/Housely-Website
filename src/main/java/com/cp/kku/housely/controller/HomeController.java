package com.cp.kku.housely.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/home")
	public String home() {
		return "home"; // ชื่อของไฟล์ home.html
	}

	@GetMapping("/admin/home")
	public String adminHome(){
		return "index";
	}
}
