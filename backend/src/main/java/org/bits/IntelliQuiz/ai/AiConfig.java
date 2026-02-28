package org.bits.IntelliQuiz.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {
    @Bean
    public ChatClient getCodellama(OllamaChatModel ollamaChatModel){
        System.out.println("Ollama configured.....");
        return ChatClient.builder(ollamaChatModel).build();
    }
}
