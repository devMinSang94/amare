package com.example.amare.dao;

import com.example.amare.dto.Match;
import com.example.amare.dto.User;

import java.util.List;

public interface MatchDao
{
    void insertMatch(Match match);
    String ConfirmMatch(Match match);
    List<User> GetMatchUser(String user_tag);
}
