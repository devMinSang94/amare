package com.example.amare.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User
{
    @ApiModelProperty(value="사용자 아이디", example = "gachon01", required = true)
    String user_id;

    @ApiModelProperty(value="사용자 비밀번호", example = "gachon0121", required = true)
    String user_password;

    @ApiModelProperty(value="사용자 닉네임", example = "가천이", required = true)
    String user_nickname;

    @ApiModelProperty(value="사용자 나이", example = "24", required = true)
    int user_age;

    @ApiModelProperty(value="사용자 주소", example = "경기도 성남시 복정구", required = true)
    String user_address;

    @ApiModelProperty(value="사용자 성별", example = "남성", required = true)
    String user_sex;

    @ApiModelProperty(value="사용자 키", example = "173", required = true)
    int user_height;

    @ApiModelProperty(value="사용자 연예인 태그 ", example = "손나은", required = true)
    String user_tag;

}
