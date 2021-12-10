package com.example.amare.controller;

import com.example.amare.dao.UserDao;
import com.example.amare.dto.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.apache.commons.io.IOUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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

    @PostMapping("/upload_photo")
    public Map<String, String> UploadImage(@RequestParam("photo") MultipartFile file, HttpServletRequest request)
    {
        Map<String, String> result = new HashMap<>();

        String os = System.getProperty("os.name").toLowerCase();
        System.out.println(os);
        try
        {
            String realPath = "";
            String relativeFolder = "";
            // 로컬 환경 테스트 Mac
            if (os == "mac os x"){
                realPath = request.getServletContext().getRealPath("/");
                System.out.println("realPath : " + realPath);
                relativeFolder = File.separator +  "static" + File.separator + "profile"+ File.separator;
                System.out.println("relativeFolder : " + relativeFolder);
            }
            else // 리눅스 서버일 경우
            {
                realPath = "/home/ubuntu/amare/src/main/webapp";
                relativeFolder = File.separator +  "static" + File.separator + "profile"+ File.separator;

            }

            File dest = new File(realPath + relativeFolder + file.getOriginalFilename());


            file.transferTo(dest);
            result.put("result", "success");

        } catch (Exception e)
        {
            e.printStackTrace();
            result.put("result", "fail");
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
    public Map<String, Object> user_login(@RequestBody Map<String, String> GivenId)
    {
        Map<String, Object> result = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
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

                    User loginedUser = userDao.findUserById(GivenId.get("user_id"));
                    String jsonloginedUser = mapper.writeValueAsString(loginedUser);
                    System.out.println(loginedUser.toString());

                    result.put("user", jsonloginedUser);
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


    @GetMapping("/getprofile")
    public  ResponseEntity<Resource> GetProfileImg(@RequestParam String user_id, HttpServletRequest request) throws IOException
    {
        String os = System.getProperty("os.name").toLowerCase();
        System.out.println("user_id : " + user_id);
        String realPath = "";
        String relativeFolder = "";

        // 로컬 환경 테스트 Mac
        if (os == "mac os x"){
            realPath = request.getServletContext().getRealPath("/");
            System.out.println("realPath : " + realPath);
            relativeFolder = File.separator +  "static" + File.separator + "profile"+ File.separator;
            System.out.println("relativeFolder : " + relativeFolder);
        }
        else // 리눅스 서버일 경우
        {
            realPath = "/home/ubuntu/amare/src/main/webapp";
            relativeFolder = File.separator +  "static" + File.separator + "profile"+ File.separator;

        }
//
//        String realPath = request.getServletContext().getRealPath("/");
//        System.out.println("realPath : " + realPath);
//        String relativeFolder = File.separator +  "static" + File.separator + "profile"+ File.separator;
//        System.out.println("relativeFolder : " + relativeFolder);

        Resource resource = new UrlResource("file:" + realPath+ relativeFolder+ user_id + ".jpg");

        return ResponseEntity.ok().body(resource);
    }



}


