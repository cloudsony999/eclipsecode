package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

	@GetMapping("/check")
	public String check() {
		return "<body bgcolor='yellow' text='red'><h1>spring security testing</h1></body>";
	}

}
