package com.benit.team.services;

import com.benit.team.base.BenitTeamContext;
import com.benit.team.common.Constant;
import com.benit.team.entities.mongo.user.User;
import com.benit.team.entities.mongo.user.UserResponse;
import com.benit.team.exception.InValidObjectException;
import com.benit.team.mongo.repositories.UserRepository;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final BenitTeamContext benitTeamContext;
    private final MapperFacade userMapperFacade;
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    public UserService(BenitTeamContext benitTeamContext,
                       MapperFacade userMapperFacade,
                       UserRepository userRepository,
                       PasswordEncoder bCryptPasswordEncoder) {
        this.benitTeamContext = benitTeamContext;
        this.userMapperFacade = userMapperFacade;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByUserNameAndPass(String userName, String password) {
        User user = findByUserName(userName);
        if (user == null)
            return null;

        return bCryptPasswordEncoder.matches(password, user.password) ? user : null;
    }

    public List<User> findUsers(Integer page, Integer limit) {
        return userRepository.findUsers(
                Optional.ofNullable(page).orElse(1),
                Optional.ofNullable(limit).orElse(Constant.DEFAULT_PAGE_SIZE)
        );
    }

    public List<User> findUsers(Integer page) {
        return findUsers(page, Constant.DEFAULT_PAGE_SIZE);
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    public UserResponse getByUserId(String userId) {
        User user = findByUserId(userId);
        UserResponse userResponse = userMapperFacade.map(user, UserResponse.class);
        return userResponse;
    }

    public Long remove(String userId) {
        return userRepository.remove(userId);
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public User findByEmail(String email) {
//        return userRepository.findByEmail(email);
        return null;
    }

    public User save(User user) throws InValidObjectException {
        if (StringUtils.isBlank(user.userId)) {
            user.userId = new ObjectId().toString();
            user.password = bCryptPasswordEncoder.encode(user.password);
            user.active = true;
            user.createdAt = new Date().getTime();
            user.updatedAt = new Date().getTime();
            if (CollectionUtils.isEmpty(user.roleGroupIds)) {
                user.roleGroupIds = Arrays.asList("USER");
            }

            return userRepository.save(user);
        }

        User existUser = findByUserId(user.userId);
        if (existUser == null)
            throw new InValidObjectException("user not found");

        setUpdateValue(existUser, user);
        return userRepository.save(existUser);
    }

    public void setUpdateValue(User oldUser, User newUser) {
        oldUser.name = StringUtils.isBlank(newUser.name) ? oldUser.name : newUser.name;
        oldUser.intro = StringUtils.isBlank(newUser.intro) ? oldUser.intro : newUser.intro;
        oldUser.description = StringUtils.isBlank(newUser.description) ? oldUser.description : newUser.description;
        oldUser.avatar = StringUtils.isBlank(newUser.avatar) ? oldUser.avatar : newUser.avatar;
        oldUser.dateOfBirth = newUser.dateOfBirth == null ? oldUser.dateOfBirth : newUser.dateOfBirth;
        oldUser.gender = newUser.gender == null ? oldUser.gender : newUser.gender;
        oldUser.active = newUser.active == null ? oldUser.active : newUser.active;
        oldUser.roleGroupIds = CollectionUtils.isEmpty(newUser.roleGroupIds) ? oldUser.roleGroupIds : newUser.roleGroupIds;
        oldUser.updatedAt = new Date().getTime();
    }

    public List<User> findByUserId(List<String> userIds) {
//        return userRepository.findByUserId(userIds);
        return Collections.emptyList();
    }

    public List<UserResponse> getByUserIds(List<String> userIds) {
        List<User> users = findByUserId(userIds);
        return userMapperFacade.mapAsList(users, UserResponse.class);
    }

    public User updateActive(String userId, Boolean active) {
        User user = findByUserId(userId);
        user.active = active;

//        return save(user);
        return null;
    }
}
