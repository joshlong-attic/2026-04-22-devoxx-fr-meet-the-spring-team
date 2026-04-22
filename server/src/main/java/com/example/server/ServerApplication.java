package com.example.server;

import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}

@Service
class SchedulingService {

    @McpTool(description = "schedule an appointment to pick up or adopt a dog from a Pooch Palace location")
    Instant schedule(@McpToolParam int dogId) {
        var i = Instant
                .now()
                .plus(3, ChronoUnit.DAYS);
        var user = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        IO.println("scheduling " + user + " for dog " + dogId + " at " + i + "");
        return i;
    }
}

@Controller
@ResponseBody
class MessageController {

    @GetMapping("/message")
    Map<String, String> me(Principal principal) {
        return Map.of("message", "Hello, " + principal.getName() + "!");
    }

}