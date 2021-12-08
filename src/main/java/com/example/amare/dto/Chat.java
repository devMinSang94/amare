package com.example.amare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Chat
{
    int chat_no;
    int room_no;
    String chat_content;
    String chat_createAt;
    String chat_content_emotion;
}
