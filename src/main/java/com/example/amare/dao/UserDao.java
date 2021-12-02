package com.example.amare.dao;

import com.example.amare.dto.User;


import java.util.List;

public interface UserDao
{
    void insertUser(User user);
    List<User> selectUsers();
    User findUserById(String id);


}
