package com.example.amare.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class ChattingController
{
    @PostMapping("/sendChat")
    public void sendChat()
    {

    }
}
