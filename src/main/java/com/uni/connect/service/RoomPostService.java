package com.uni.connect.service;

import com.uni.connect.dao.PostRoomRepo;
import com.uni.connect.dao.UserRepo;
import com.uni.connect.model.RoomPost;
import com.uni.connect.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomPostService {

    @Autowired
    PostRoomRepo postRoomRepo;

    @Autowired
    UserRepo userRepo;

    public ResponseEntity<String> createRoomPost(String username, RoomPost roomPost) {
        Optional<User> fetchedUser = userRepo.findByUsername(username);
        if (fetchedUser.isPresent()) {
            User user = fetchedUser.get();
            roomPost.setUsername(username);
            user.setRoomPost(roomPost);
            userRepo.save(user);
            postRoomRepo.save(roomPost);
            return ResponseEntity.ok("Successfully created room post");
        }
        return ResponseEntity.ok("Error");
    }

    public ResponseEntity<List<RoomPost>> getAllRoomPostings() {

        List<RoomPost> roomPosts = postRoomRepo.findAll();

        if (roomPosts.isEmpty()) {
            return ResponseEntity.ok().body(null);
        }
        return ResponseEntity.ok(roomPosts);
    }

    public ResponseEntity<RoomPost> getUserRoomPosting(String username) {

        Optional<RoomPost> roomPost = postRoomRepo.findByUsername(username);

        if (roomPost.isEmpty()) {
            return ResponseEntity.ok().body(null);
        }
        return new ResponseEntity<>(roomPost.get(), HttpStatus.OK);
    }
}
