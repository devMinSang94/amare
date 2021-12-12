package com.example.amare.controller;

import com.example.amare.dao.ChatDao;
import com.example.amare.dto.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        List<Member> member = chatdao.GetChatRoom(user_id.get("user_id"));
        System.out.println(member.get(0).toString());
        System.out.println(member.get(1).toString());


        result.put("result","success");
        result.put("members",member);

        return result;
    }
}
