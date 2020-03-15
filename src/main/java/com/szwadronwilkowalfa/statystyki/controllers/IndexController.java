package com.szwadronwilkowalfa.statystyki.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
	private static final String INDEX_VIEW = "index";
	
	@GetMapping("/")
	public String showIndexView(ModelMap modelMap) {
		modelMap.put("name", "Hello");
		return INDEX_VIEW;
	}
	
}
