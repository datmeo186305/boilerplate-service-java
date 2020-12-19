package com.benit.team.mongo.repositories;

import com.benit.team.common.Constant;
import com.benit.team.entities.mongo.user.User;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public List<User> findUsers(Integer limit, Integer page) {
//        Query query = new Query(
//                limit: limit,
//                skip: limit * (page - 1 < 0 ? 0 : page - 1)
//        );
//        return mongoTemplate.find(query, User.class);
        return Collections.emptyList();
    }

    public User save(User user) {
        return mongoTemplate.save(user);
    }

    public User findByUserId(String userId) {
        Query query = new Query().addCriteria(Criteria.where("user_id").is(userId));
        return mongoTemplate.findOne(query, User.class);
    }

    public Long remove(String userId) {
//        Query query = new Query(Criteria.where("user_id").is(userId));
//        return mongoTemplate.remove(query, User.class).deletedCount;
        return 1L;
    }

    public User findByUserName(String userName) {
        Query query = new Query().addCriteria(Criteria.where("user_name").is(userName));
        return mongoTemplate.findOne(query, User.class);
    }

    User findByEmail(String email) {
        Query query = new Query().addCriteria(Criteria.where("email").is(email));
        return mongoTemplate.findOne(query, User.class);
    }

    List<String> findRoleByUserId(String userId) {
//        List<String> result = new ArrayList<>();
//        mongoTemplate.getCollection(Constant.USERS).find()
//                .filter(new Document().append("user_id", userId))
//                .projection(new Document().append("role_ids", 1))
//                .iterator()
//                .forEachRemaining({item -> result.add(item != null ? item.role_ids : null as String)});
//        return result;
        return Collections.emptyList();
    }

    List<User> findByUserId(List<String> userIds) {
        Query query = new Query(Criteria.where("user_id").in(userIds));
        return mongoTemplate.find(query, User.class);
    }

    void updateNumberFollow(String userId, Integer quantity) {
//        Document filter = [user_id: userId];
//        Document update = [
//                $inc: [number_follow: quantity];
//        ]
//
//        mongoTemplate.getCollection(Constant.USERS).updateOne(filter, update);
    }
}
