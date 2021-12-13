package com.example.amare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Chat
{
    int chat_no;
    String user_id;
    int room_no;
    String chat_content;
    String chat_createAt;
    String chat_content_emotion;
}
