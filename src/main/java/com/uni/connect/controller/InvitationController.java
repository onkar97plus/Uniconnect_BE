package com.uni.connect.controller;

import com.uni.connect.dao.UserRepo;
import com.uni.connect.model.User;
import com.uni.connect.service.CrudService;
import com.uni.connect.service.InvitationService;
import com.uni.connect.service.JwtService;
import com.uni.connect.service.UserDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class InvitationController {

    @Autowired
    JwtService jwtService;

    @Autowired
    UserDetailsServiceImp userDetailsService;

    @Autowired
    UserRepo userRepo;

    @Autowired
    CrudService crudService;

    @Autowired
    InvitationService invitationService;

    @PutMapping("/sendconnreq")
    public void sendConnectionRequest(@RequestHeader("Authorization") String token, @RequestParam String sendReqToUser){

        String username = jwtService.extractUsername(token.substring(7));
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if(jwtService.isValid(token.substring(7), userDetails)){

            invitationService.sendInvitations(username, sendReqToUser);

        }
    }

    @GetMapping("/sentInvitations")
    public ResponseEntity<List<User>> getSentInvitations(@RequestHeader("Authorization") String token){
        String username = jwtService.extractUsername(token.substring(7));

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if(jwtService.isValid(token.substring(7), userDetails)){
            return invitationService.getSentInvitations(username);
        }else {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/acceptInvitation")
    public ResponseEntity<String> acceptInvitation(@RequestHeader("Authorization") String token, @RequestParam String acceptUser){

        String username = jwtService.extractUsername(token.substring(7));

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if(jwtService.isValid(token.substring(7), userDetails)){
            ResponseEntity<String> acceptedConnection = invitationService.acceptInvitation(username, acceptUser);

            return acceptedConnection;
        }

        return null;
    }


}
