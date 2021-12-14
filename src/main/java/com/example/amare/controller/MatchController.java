package com.example.amare.controller;

import com.example.amare.dao.MatchDao;
import com.example.amare.dao.MemberDao;
import com.example.amare.dao.RoomDao;
import com.example.amare.dto.Match;
import com.example.amare.dto.Member;
import com.example.amare.dto.User;
import com.example.amare.dto.User_tags;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    @Autowired
    private MemberDao memberdao;

    /*
        "/api/match"
        - 내가 상대방을 매칭시 DB에 매칭 정보를 삽입함 ( MatchDao.insertMatch)
        - 만약 상대방이 나를 매칭(MatchDao.ConfirmMatch) 했다면 채팅방을 생성 (Room.insertRoom)
        - 그 채팅방에 해당하는 멤버 DB 를 매칭된 사용자들의 정보를 넣어줌 (Member.insertMember)
     */
    @PostMapping("/match")
    public Map<String, String> match(@RequestBody Match matchUser)
    {
        // response 객체
        Map<String, String> result = new HashMap<>();

        try
        {
            // DB에 매치한 아이디를 추가함
            matchdao.insertMatch(matchUser);
            String confirm = matchdao.ConfirmMatch(matchUser);
            System.out.println(confirm);
            // 만약 상대방이 나를 매칭 했으면 채팅방 생성
            if (matchUser.getMatch_myid().equals(matchdao.ConfirmMatch(matchUser)))
            {
                // Start Room insert
                // 채팅방 생성 Auto_increment 로 자동생성후
                // 가장 최근에 만들어진 채팅방 번호를 가져온다.
                roomdao.insertRoom();
                int roomNo = roomdao.selectRoom();

                // Start 매칭된 회원을 만들어진 채팅방 멤버로 insert
                // End Room insert

                Member member1 = new Member(roomNo, matchUser.getMatch_myid());
                Member member2 = new Member(roomNo, matchUser.getMatch_selected_id());

                memberdao.insertMember(member1);
                memberdao.insertMember(member2);
                result.put("chat","true");
                // End 매칭된 회원을 만들어진 채팅방 멤버로 insert

            }
            result.put("result", "success");
            result.put("chat","false");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    @PostMapping("/getmatchusers")
    Map<String, Object> GetMatchUsers(@RequestBody Map<String,ArrayList<String>> user_tags)
    {

        Map<String, Object> result = new HashMap<>();
        System.out.println(user_tags);
        List<User> users = new ArrayList<>();
        ArrayList<String> tags = user_tags.get("user_tag");
        try
        {
            for (int i =0 ; i< tags.size(); i++)
            {
                users.addAll(matchdao.GetMatchUser(tags.get(i)));
            }
            System.out.println(users);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        result.put("users", users);
        System.out.println(result);
        return result;
    }



    @PostMapping("/givenLikeList")
    public Map<String, Object> GetGivenLikeList (@RequestBody Map<String, String> user)
    {
        Map<String, Object> result = new HashMap<>();

        // 나를 좋아하는 유저들의 아이디 리스트
        ArrayList<String> Users_LikeMe = new ArrayList<>();
        // 나를 좋아하는 유저들의 객체정보 리스트
        ArrayList<User> users = new ArrayList<>();

        try{
            // 1. 나름 좋아하는 유저들을 리스트에 저장
            Users_LikeMe = matchdao.GetGivenLikeList(user.get("user"));
            System.out.println("Users_LikeMe"+Users_LikeMe.toString());

            //2. 리스트의 저장된 멤버들의 user 객체를 리스트에 저장하고 response 함
            for (int i =0; i<Users_LikeMe.size();i++)
            {
                users.add(matchdao.GetGivenLikeUser(Users_LikeMe.get(i)));

            }
            System.out.println("나를좋아하는 유저"+users.toString());
            result.put("GivenLike",new ObjectMapper().writeValueAsString(users));
        }catch (Exception e)
        {
            e.printStackTrace();

        }
        return result;
    }
}
