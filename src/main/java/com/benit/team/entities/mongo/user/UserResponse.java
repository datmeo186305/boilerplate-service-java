package com.benit.team.entities.mongo.user;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponse {

    private String userId;
    private String name;
    private Long dateOfBirth;
    private Integer gender;
    private String userName;
    private String email;
    private Boolean active;
    private String avatar;
    private List<String> roleGroupIds;
    private String intro;
    private String description;
    private Integer numberFollow;
    private String userStatus;
    private Long createdAt;
    private Long updatedAt;

}
