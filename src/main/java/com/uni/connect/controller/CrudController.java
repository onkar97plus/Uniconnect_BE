package com.uni.connect.controller;

import com.uni.connect.dao.UserRepo;
import com.uni.connect.model.Enums.RoommateSearchStatus;
import com.uni.connect.model.User;
import com.uni.connect.service.CrudService;
import com.uni.connect.service.JwtService;
import com.uni.connect.service.UserDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CrudController {

    @Autowired
    JwtService jwtService;

    @Autowired
    UserDetailsServiceImp userDetailsService;

    @Autowired
    UserRepo userRepo;

    @Autowired
    CrudService crudService;

    @GetMapping("/getUsers")
    public List<User> retrieve(@RequestHeader("Authorization") String token){

        String username = jwtService.extractUsername(token.substring(7));

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(jwtService.isValid(token.substring(7), userDetails)){
            return userRepo.findAll();
        }
        else
            return null;
    }

    @PutMapping("/updateProfile")
    public ResponseEntity<String> UpdateUserProfile(@RequestHeader("Authorization") String token, @RequestBody User userRequest){
        String username = jwtService.extractUsername(token.substring(7));
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if(jwtService.isValid(token.substring(7), userDetails)){

            return crudService.updateProfile(userRequest, username);

        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token or token expired.");
        }
    }

    @GetMapping("/getUser")
    public ResponseEntity<User> getUser(@RequestHeader("Authorization") String token){
        String username = jwtService.extractUsername(token.substring(7));
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(jwtService.isValid(token.substring(7), userDetails)){
            return crudService.getUser(username);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getUsersByUniversity")
    public ResponseEntity<List<User>> getUsersByUniversity(@RequestHeader("Authorization") String token, @RequestParam String university){

        String username = jwtService.extractUsername(token.substring(7));
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(jwtService.isValid(token.substring(7), userDetails)){
            return crudService.getUserByUniAndisActive(university, username, RoommateSearchStatus.ACTIVE);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
