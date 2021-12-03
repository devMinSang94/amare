package com.example.amare.controller;

import com.example.amare.dao.UserDao;
import com.example.amare.dto.User;
import io.swagger.annotations.*;
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_id", value = "사용 아이디,", required = true, dataType = "String"),
            @ApiImplicitParam(name = "user_password", value = "사용자 비밀번호,", required = true, dataType = "String"),
            @ApiImplicitParam(name = "user_nickname", value = "사용자 닉네임,", required = true, dataType = "String"),
            @ApiImplicitParam(name = "user_age", value = "사용자 나이,", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "user_address", value = "사용자 주소,", required = true, dataType = "String"),
            @ApiImplicitParam(name = "user_sex", value = "사용자 성별,", required = true, dataType = "String"),
            @ApiImplicitParam(name = "user_height", value = "사용자 키,", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "user_tag1", value = "사용자 연예인 태그1,", required = true, dataType = "String"),
            @ApiImplicitParam(name = "user_tag1_probability", value = "사용자 연예인 태그1 확률 ,", required = true, dataType = "Float"),
            @ApiImplicitParam(name = "user_tag2", value = "사용자 연예인 태그2,", required = true, dataType = "String"),
            @ApiImplicitParam(name = "user_tag2_probability", value = "사용자 연예인 태그2 확률 ,", required = true, dataType = "Float"),
            @ApiImplicitParam(name = "user_tag3", value = "사용자 연예인 태그3,", required = true, dataType = "String"),
            @ApiImplicitParam(name = "user_tag3_probability", value = "사용자 연예인 태그3 확률 ,", required = true, dataType = "Float")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그인 성공"),
            @ApiResponse(code = 400, message = "잘못된 접근"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_id", value = "사용 아이디,", required = true, dataType = "String"),
            @ApiImplicitParam(name = "user_password", value = "사용자 비밀번호,", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그인 성공"),
            @ApiResponse(code = 400, message = "잘못된 접근"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    @PostMapping("/login")
    public Map<String, String> user_login(@RequestBody Map<String, String> GivenId)
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


