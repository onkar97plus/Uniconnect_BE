package com.uni.connect.dao;

import com.uni.connect.model.RoomPost;
import com.uni.connect.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRoomRepo extends MongoRepository<RoomPost,String> {

    Optional<RoomPost> findByUsername(String username);
}
