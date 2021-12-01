package com.example.amare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User
{
    String user_id;
    String user_password;
    String user_nickname;
    int user_age;
    String user_address;
    String user_sex;
    int user_height;
    String user_tag;

}
