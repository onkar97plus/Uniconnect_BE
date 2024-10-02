package com.uni.connect.controller;

import com.uni.connect.model.RoomPost;
import com.uni.connect.service.JwtService;
import com.uni.connect.service.RoomPostService;
import com.uni.connect.service.UserDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PostingController {

    @Autowired
    JwtService jwtService;

    @Autowired
    UserDetailsServiceImp userDetailsService;

    @Autowired
    RoomPostService roomPostService;

    @PutMapping("/createRoomPost")
    public ResponseEntity<?> saveLatestInvitationRequest(@RequestHeader("Authorization") String token, @RequestBody RoomPost roomPost){
        String username = jwtService.extractUsername(token.substring(7));

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if(jwtService.isValid(token.substring(7), userDetails)){

            return ResponseEntity.ok(roomPostService.createRoomPost(username,roomPost));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/getUserPostings")
    public ResponseEntity<?> getUserPostings(@RequestHeader("Authorization") String token){
        String username = jwtService.extractUsername(token.substring(7));

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if(jwtService.isValid(token.substring(7), userDetails)){

            return ResponseEntity.ok(roomPostService.getUserRoomPosting(username));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/getAllRoomPostings")
    public ResponseEntity<?> getAllRoomPostings(@RequestHeader("Authorization") String token){
        String username = jwtService.extractUsername(token.substring(7));

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if(jwtService.isValid(token.substring(7), userDetails)){

            return ResponseEntity.ok(roomPostService.getAllRoomPostings());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
