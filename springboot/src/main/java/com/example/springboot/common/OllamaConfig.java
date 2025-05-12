package com.example.springboot.common;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OllamaConfig {

	@Bean
	public OllamaChatModel ollamaChatModel() {
		OllamaApi ollamaApi = new OllamaApi("http://localhost:11434");
		OllamaOptions options = OllamaOptions.create()
			.withModel("deepseek-r1:1.5b-qwen-distill-q8_0"); // 指定模型名称
		return new OllamaChatModel(ollamaApi, options);
	}
}