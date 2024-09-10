package com.uni.connect.dao;

import com.uni.connect.model.RoomPost;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRoomRepo extends MongoRepository<RoomPost,String> {
}
