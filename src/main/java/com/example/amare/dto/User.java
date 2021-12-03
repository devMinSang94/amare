package com.example.amare.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User
{
    @ApiModelProperty(value = "사용자 아이디", example = "gachon01", required = true)
    String user_id;

    @ApiModelProperty(value = "사용자 비밀번호", example = "gachon0121", required = true)
    String user_password;

    @ApiModelProperty(value = "사용자 닉네임", example = "가천이", required = true)
    String user_nickname;

    @ApiModelProperty(value = "사용자 나이", example = "24", required = true)
    int user_age;

    @ApiModelProperty(value = "사용자 주소", example = "경기도 성남시 복정구", required = true)
    String user_address;

    @ApiModelProperty(value = "사용자 성별", example = "남성", required = true)
    String user_sex;

    @ApiModelProperty(value = "사용자 키", example = "173", required = true)
    int user_height;

    @ApiModelProperty(value = "사용자 연예인 태그1 ", example = "손나은", required = true)
    String user_tag1;


    @ApiModelProperty(value = "사용자 연예인 태그1 확률 ", example = "90.2", required = true)
    float user_tag1_probability;


    @ApiModelProperty(value = "사용자 연예인 태그2 ", example = "아이유", required = true)
    String user_tag2;


    @ApiModelProperty(value = "사용자 연예인 태그2 확률 ", example = "77.6", required = true)
    float user_tag2_probability;


    @ApiModelProperty(value = "사용자 연예인 태그3 ", example = "수지", required = true)
    String user_tag3;


    @ApiModelProperty(value = "사용자 연예인 태그3 확률 ", example = "68.6", required = true)
    float user_tag3_probability;
}
