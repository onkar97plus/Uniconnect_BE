package com.uni.connect.controller;

import com.uni.connect.dao.UserRepo;
import com.uni.connect.model.User;
import com.uni.connect.service.CrudService;
import com.uni.connect.service.InvitationService;
import com.uni.connect.service.JwtService;
import com.uni.connect.service.UserDetailsServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(InvitationController.class);
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

        System.out.println("uid::" +sendReqToUser);

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

    @GetMapping("/incomingInvitations")
    public ResponseEntity<List<User>> getIncomingInvitations(@RequestHeader("Authorization") String token){
        String username = jwtService.extractUsername(token.substring(7));

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if(jwtService.isValid(token.substring(7), userDetails)){
            return invitationService.getIncomingInvitations(username);
        }else {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/acceptInvitation")
    public ResponseEntity<String> acceptInvitation(@RequestHeader("Authorization") String token, @RequestParam String acceptUser){

        log.info("In acceptInvitation controller::"+acceptUser);

        String username = jwtService.extractUsername(token.substring(7));

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if(jwtService.isValid(token.substring(7), userDetails)){
            invitationService.acceptInvitation(username, acceptUser);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/connections")
    public ResponseEntity<List<User>> getConnections(@RequestHeader("Authorization") String token){
        String username = jwtService.extractUsername(token.substring(7));

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if(jwtService.isValid(token.substring(7), userDetails)){
            return invitationService.fetchConnections(username);
        }else {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/getNewConnectionRequests")
    public ResponseEntity<Integer> connectionPollingController(@RequestHeader("Authorization") String token){
        String username = jwtService.extractUsername(token.substring(7));

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if(jwtService.isValid(token.substring(7), userDetails)){
              return new ResponseEntity<>(invitationService.connectionPolling(username), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/saveLastInvitationRequestsOnLogout")
    public ResponseEntity<Void> saveLatestInvitationRequest(@RequestHeader("Authorization") String token){
        String username = jwtService.extractUsername(token.substring(7));

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if(jwtService.isValid(token.substring(7), userDetails)){
            invitationService.saveRequestsOnLogout(username);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/getNewConnectionRequestsOnLogin")
    public ResponseEntity<Integer> newConnectionsOnLogin(@RequestHeader("Authorization") String token){
        String username = jwtService.extractUsername(token.substring(7));

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if(jwtService.isValid(token.substring(7), userDetails)){
            return new ResponseEntity<>(invitationService.getNewRequestsOnLogin(username), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


}
