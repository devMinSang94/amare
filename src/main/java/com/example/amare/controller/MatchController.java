package com.example.amare.controller;

import com.example.amare.dao.MatchDao;
import com.example.amare.dao.MemberDao;
import com.example.amare.dao.RoomDao;
import com.example.amare.dto.Match;
import com.example.amare.dto.Member;
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

                    // End 매칭된 회원을 만들어진 채팅방 멤버로 insert

                }
            result.put("result","success");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

}
