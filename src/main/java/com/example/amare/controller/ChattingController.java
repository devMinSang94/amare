package com.example.amare.controller;

import com.example.amare.dao.ChatDao;
import com.example.amare.dto.Chat;
import com.example.amare.dto.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ChattingController
{

    @Autowired
    ChatDao chatdao;

    // 해당 멤버의 채팅방 번호를 반환해주는 함수
    @PostMapping("/getChatRoom")
    public Map<String, Object> getChatRoom(@RequestBody Map<String,String> user_id)
    {
        Map<String, Object> result = new HashMap<>();
        System.out.println("getChatRoom: user_id"+ user_id.get("user_id"));
        List<Member> member = chatdao.GetChatRoom(user_id.get("user_id"));
        System.out.println(member);
        System.out.println(member.get(0).toString());
        System.out.println(member.get(1).toString());

        Chat LastChat = chatdao.GetLastMessage(member.get(0).getRoom_no());

        if (LastChat != null)
            result.put("LastMessage",LastChat.getChat_content());
        else
            result.put("LastMessage","");


        result.put("result","success");
        result.put("members",member);

        return result;
    }


    @PostMapping("/sendChat")
    public Map<String, Object> SendChat(@RequestBody Chat chat)
    {
        Map<String, Object> result = new HashMap<>();

        try
        {
            System.out.println(chat);
            chatdao.SendChat(chat);
            System.out.println(chat.getUser_id()+": sendSuccess");

            Chat sendChat = chatdao.GetSendChat(chat);
            ObjectMapper mapper = new ObjectMapper();
            String jsonloginedUser = mapper.writeValueAsString(sendChat);

            System.out.println("sendChat: "+ jsonloginedUser);
            result.put("sendChat",jsonloginedUser);
            result.put("result","success");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return result;
    }

    @PostMapping("/GetAllChat")
    public Map<String,Object> GetChat(@RequestBody Map<String, String> room_no)
    {
        ArrayList<Chat> chats;
        Map<String, Object> result = new HashMap<>();

        try
        {
             chats = chatdao.GetAllChat(Integer.parseInt(room_no.get("room_no")));
            System.out.println(chats);
            result.put("chats",chats);

        }catch (Exception e )
        {
            e.printStackTrace();
        }
        result.put("result","success");
        return result;
    }

    @PostMapping("/GetLastMessage")
    public Map<String, String> GetLastMessage(@RequestBody Map<String, String> room_no)
    {
        Map<String, String> result = new HashMap<>();
        try{
            System.out.println("GetLastMessage_requestRoomno:"+room_no.get("room_no"));
            Chat LastChat = chatdao.GetLastMessage(Integer.parseInt(room_no.get("room_no")));

            if (LastChat != null)
                result.put("LastMessage",LastChat.getChat_content());
            else
                result.put("LastMessage","");

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("LastMessage: "+result);
        return result;

    }
}
