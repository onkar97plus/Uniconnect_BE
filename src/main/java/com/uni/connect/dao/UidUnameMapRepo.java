package com.uni.connect.dao;

import com.uni.connect.model.UidUnameMap;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UidUnameMapRepo extends MongoRepository<UidUnameMap,String> {
     UidUnameMap findByUid(String uid);
}
