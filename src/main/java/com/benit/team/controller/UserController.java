package com.benit.team.controller;

import com.benit.team.base.BaseController;
import com.benit.team.base.response.BaseResponse;
import com.benit.team.base.response.ResponseData;
import com.benit.team.base.response.ResponseError;
import com.benit.team.entities.mongo.user.User;
import com.benit.team.entities.mongo.user.UserRequest;
import com.benit.team.entities.mongo.user.UserResponse;
import com.benit.team.services.JwtService;
import com.benit.team.services.UserService;
import com.nimbusds.jose.JOSEException;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    public MapperFacade userMapperFacade;
    @Autowired
    public UserService userService;
    @Autowired
    public JwtService jwtService;

    @GetMapping
    public ResponseEntity<ResponseData> findUsers(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pageSize) {
        ResponseData responseData = new ResponseData();
        return new ResponseEntity<ResponseData>(responseData, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse> login(@RequestBody UserRequest userRequest) throws JOSEException {
        User user = userService.findUserByUserNameAndPass(userRequest.getUserName(), userRequest.getPassword());
        if (user == null) {
            ResponseError error = new ResponseError();
            error.setStatusCode(401);
            error.setError("Thông tin tài khoản hoặc mật khẩu không chính xác");
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }
        String token = jwtService.generateToken(userRequest.getUserName());
        UserResponse userResponse = userMapperFacade.map(user, UserResponse.class);
        ResponseData data = new ResponseData();
        Map<String, Object> mapData = new HashMap<>();
        mapData.put("access_token", token);
        mapData.put("user", userResponse);
        data.setStatusCode(200);
        data.setData(mapData);
        return new ResponseEntity<>(data, HttpStatus.OK);

    }
}
