package com.example.springboot.controller;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ollama")
public class OllamaClientController {
	@Autowired
	private OllamaChatModel ollamaChatModel;

	@GetMapping("/chat/v1")
	public String ollamaChat(@RequestParam String msg){
		return ollamaChatModel.call(msg);
	}

	@GetMapping("/chat/v2")
	public Flux<String> ollamaChat2(@RequestParam String msg){
		Flux<String> stream = ollamaChatModel.stream(msg);
		return stream;
	}
}
