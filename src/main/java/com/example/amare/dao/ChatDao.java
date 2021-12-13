package com.example.amare.dao;

import com.example.amare.dto.Chat;
import com.example.amare.dto.Member;

import java.util.ArrayList;
import java.util.List;

public interface ChatDao
{
    void SendChat(Chat chat);
    List<Member> GetChatRoom(String user_id);
    ArrayList<Chat> GetAllChat(int room_no);
    Chat GetSendChat(Chat chat);
    Chat GetLastMessage(int room_no);
}
