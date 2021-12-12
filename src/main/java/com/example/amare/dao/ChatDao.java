package com.example.amare.dao;

import com.example.amare.dto.Chat;
import com.example.amare.dto.Member;

import java.util.List;

public interface ChatDao
{
    void insertChat(Chat chat);
    List<Member> GetChatRoom(String user_id);
}
