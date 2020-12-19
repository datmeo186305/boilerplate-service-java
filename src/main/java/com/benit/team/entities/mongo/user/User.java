package com.benit.team.entities.mongo.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document("users")
public class User {

    public String id;
    public String userId;
    public String userName;
    public String password;
    public List<String> roleGroupIds;
    public String email;
    public String name;
    public Long dateOfBirth;
    public Integer gender;
    public Boolean active;
    public String avatar;
    public String intro;
    public String description;
    public Integer numberFollow;
    public Long createdAt;
    public Long updatedAt;

}
