package br.com.alura.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

	@RequestMapping("/")
	@ResponseBody
	public String hello() {
		System.out.println("Olá Spring Boot!!");
		
		return "<h1>Hello Word, Spring Boot Man!!</h1>";
	}
	
}
