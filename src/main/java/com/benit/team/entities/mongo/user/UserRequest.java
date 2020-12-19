package com.benit.team.entities.mongo.user;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRequest {

    private String userId;
    private String name;
    private Long dateOfBirth;
    private Integer gender;
    private String userName;
    private String password;
    private List<String> roleGroupIds;
    private String email;
    private Boolean active;
    private String avatar;
    private String intro;
    private String description;

}
