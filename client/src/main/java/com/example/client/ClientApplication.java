package com.example.client;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.annotation.ClientRegistrationId;
import org.springframework.security.oauth2.client.web.client.support.OAuth2RestClientHttpServiceGroupConfigurer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.registry.ImportHttpServices;

@ImportHttpServices(MessageClient.class)
@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Bean
    OAuth2RestClientHttpServiceGroupConfigurer auth2RestClientHttpServiceGroupConfigurer(
            OAuth2AuthorizedClientManager auth2AuthorizedClientManager) {
        return OAuth2RestClientHttpServiceGroupConfigurer
                .from(auth2AuthorizedClientManager);
    }
}

@Controller
@ResponseBody
class AgentController {

    private final ChatClient ai;


    AgentController(
            ToolCallbackProvider toolCallbackProvider,
            ChatClient.Builder ai) {
        this.ai = ai
                .defaultSystem("""
                        You are an AI powered assistant to help people adopt a dog from the adoptions agency named Pooch Palace with locations in Paris, Seoul, Tokyo, Singapore, Paris, Mumbai, New Delhi, Barcelona, San Francisco, and London. Information about the dogs availables will be presented below. If there is no information, then return a polite response suggesting we don't have any dogs available.
                        
                        If somebody asks you about animals, and there's no information in the context, then feel free to source the answer from other places.
                        
                        If somebody asks for a time to pick up the dog, don't ask other questions: simply provide a time by consulting the tools you have available.
                        
                        """)
                .defaultToolCallbacks(toolCallbackProvider)
                .build();
    }

    @GetMapping("/ask")
    String ask() {
        return this.ai
                .prompt()
                .user("when can i pick up Prancer from the Paris Pooch Palace location?")
                .call()
                .content();
    }
}

@Controller
@ResponseBody
class ClientController {

    private final MessageClient messageClient;

    ClientController(MessageClient messageClient) {
        this.messageClient = messageClient;
    }


    @GetMapping("/client")
    Message message(
//            @RegisteredOAuth2AuthorizedClient("spring") OAuth2AuthorizedClient auth2AuthorizedClient
    ) {
        return this.messageClient.get(); //auth2AuthorizedClient.getAccessToken().getTokenValue());
    }
}

record Message(String message) {
}


@ClientRegistrationId("spring")
interface MessageClient {

    @GetExchange("http://localhost:8081/message")
    Message get();
}


/*
@Component
class MessageClient {

    private final RestClient http;

    MessageClient(RestClient.Builder http, OAuth2AuthorizedClientManager auth2AuthorizedClientManager) {
        this.http = http
                .requestInterceptor(new OAuth2ClientHttpRequestInterceptor(auth2AuthorizedClientManager))
                .build();
    }

    Message get(String at) {
        return this.http
                .get()
                .uri("http://localhost:8081/message")
                .attributes(ClientAttributes.clientRegistrationId("spring"))
//                .headers(a -> a.setBearerAuth(at))
                .retrieve()
                .body(Message.class);
    }
}
*/