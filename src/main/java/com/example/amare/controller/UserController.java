package com.example.amare.controller;

import com.example.amare.dao.UserDao;
import com.example.amare.dto.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = {"User 정보를 제공하는 Controller"})
@RestController
@RequestMapping("/api")
public class UserController
{
    @Autowired
    private UserDao userDao;


    // 회원가입
    @ApiOperation(value = "클라이언트에서 회원정보를 가져와 DB에 저장하는 메서드 ", notes = "성공시 {'result' : 'success' } 실패시 {'result' : 'fail'} 을 반환합니다.")
    @PostMapping("/register")
    public Map<String, String> user_register(@RequestBody User user)
    {
        System.out.println(user.toString());

        // 결과값
        Map<String, String> result = new HashMap<>();

        try
        {
            // DB에 User 정보 삽입
            userDao.insertUser(user);

            result.put("result", "success");
        } catch (Exception e)
        {
            result.put("result", "fail");
            e.printStackTrace();
        }

        return result;

    }


    // 유저 확인
    @PostMapping("/user")
    public List<User> user()
    {
        System.out.println("db con");
        return userDao.selectUsers();
    }

    @ApiOperation(value = "클라이언트에서 아이디 비밀번호를 가져와 DB와 검사 결과를 반환하는 메서드", notes = "성공시 {'result' : 'success' } 실패시 {'result' : 'fail'} 을 반환합니다.")
    @PostMapping("/login")
    public Map<String, String> user_login( @RequestBody Map<String, String> GivenId)
    {
        Map<String, String> result = new HashMap<>();

        // 로그인
        try
        {
            // DB 에서 로그인 암호와 PW 가져와 비교
            User FindedUser = userDao.findUserById(GivenId.get("user_id"));
            System.out.println(FindedUser);

            // 가져온 객체가 null인지 값이 있는지 확인
            if (FindedUser.getUser_id() != null)
            {
                // null 이 아닌경우 pw 검사
                if (GivenId.get("user_password").equals(FindedUser.getUser_password()))
                {
                    // pw 일치 회원 로그인 가능
                    System.out.println("로그인 연결성공");

                    //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
                    //HttpSession session = request.getSession();
                    // 세션에 아이디 정보를 담는다.
                    //session.setAttribute("user",FindedUser.getUser_id());
                    //System.out.println("session-attribute: "+session.getAttribute("user"));
                    //System.out.println(session.getCreationTime());
                    // 세션값과 성공 메세지 반환
                    //result.put("token", session.getAttribute("user").toString());
                    result.put("result", "success");
                } else
                {
                    // pw 불 일치 회원 로그인 불가
                    System.out.println("로그인 연결실패");
                    result.put("result", "fail");
                }

            } else
            {
                // null 인 경우 아이디가 존재 x 로그인 실패 출력
                result.put("result", "fail");
            }


        } catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println(result.get("result"));
        return result;
    }


}


