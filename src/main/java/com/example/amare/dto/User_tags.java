package com.example.amare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class User_tags
{
    Map<String,List<Map<String,String>>> user_tag;
}
