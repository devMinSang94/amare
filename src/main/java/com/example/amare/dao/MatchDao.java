package com.example.amare.dao;

import com.example.amare.dto.Match;

public interface MatchDao
{
    void insertMatch(Match match);
    Boolean ConfirmMatch(Match match);
}
