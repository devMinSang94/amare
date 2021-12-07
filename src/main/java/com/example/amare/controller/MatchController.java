package com.example.amare.controller;

import com.example.amare.dao.MatchDao;
import com.example.amare.dao.RoomDao;
import com.example.amare.dto.Match;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api(tags = {"Match 정보를 제공하는 Controller"})
@RestController
@RequestMapping("/api")
public class MatchController
{

    @Autowired
    private MatchDao matchdao;
    @Autowired
    private RoomDao roomdao;

    @PostMapping("/match")
    public Map<String, String> match(@RequestBody Match matchUser)
    {
        // response 객체
        Map<String, String> result = new HashMap<>();

        try
        {
            // DB에 매치한 아이디를 추가함
            matchdao.insertMatch(matchUser);

            // 만약 상대방이 나를 매칭 했으면 채팅방 생성
                if (matchUser.getMatch_myid().equals(matchdao.ConfirmMatch(matchUser)))
                {
                    roomdao.insertRoom();
                    System.out.println(roomdao.selectRoom());
                }
            result.put("result","success");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

}
