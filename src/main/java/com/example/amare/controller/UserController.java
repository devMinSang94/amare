package com.example.amare.controller;

import com.example.amare.dao.UserDao;
import com.example.amare.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController
{
    @Autowired
    private UserDao userDao;


    // 회원가입
    @PostMapping("/api/register")
    public Map<String, String> user_register(@RequestBody User user)
    {
        System.out.println(user.toString());

        // 결과값
        Map<String, String> result = new HashMap<>();

        try
        {
            // DB에 User 정보 삽입
            userDao.insertUser(user);
            result.put("result","success");
        }catch (Exception e)
        {
            result.put("result","fail");
            e.printStackTrace();
        }

        return result;

    }
}
