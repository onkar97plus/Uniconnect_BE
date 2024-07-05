package com.uni.connect.service;

import com.uni.connect.dao.InvitationRepo;
import com.uni.connect.dao.UserRepo;
import com.uni.connect.model.Enums.RoommateSearchStatus;
import com.uni.connect.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CrudService {

    @Autowired
    UserRepo userRepo;


    public ResponseEntity<String> updateProfile(User userRequest, String username) {

        Optional<User> fetchedUser = userRepo.findByUsername(username);

        if (userRepo.findByUsername(username).isPresent()) {
            User existingUser = fetchedUser.get();

            if (userRequest.getEthnicity() != null) {
                existingUser.setEthnicity(userRequest.getEthnicity());
            }
            if (userRequest.getReligion() != null) {
                existingUser.setReligion(userRequest.getReligion());
            }
            if (userRequest.getFirstName() != null) {
                existingUser.setFirstName(userRequest.getFirstName());
            }
            if (userRequest.getLastName() != null) {
                existingUser.setLastName(userRequest.getLastName());
            }
            if (userRequest.getAge() != 0) {
                existingUser.setAge(userRequest.getAge());
            }
            if (userRequest.getEatingPreference() != null) {
                existingUser.setEatingPreference(userRequest.getEatingPreference());
            }
            if (userRequest.getState() != null) {
                existingUser.setState(userRequest.getState());
            }
            if (userRequest.getUniversity() != null) {
                existingUser.setUniversity(userRequest.getUniversity());
            }
            if (userRequest.getAdditionalInformation() != null) {
                existingUser.setAdditionalInformation(userRequest.getAdditionalInformation());
            }
            if (userRequest.getIntakeBatch() != null) {
                existingUser.setIntakeBatch(userRequest.getIntakeBatch());
            }
            if (userRequest.isDrinker() != existingUser.isDrinker()) {
                existingUser.setDrinker(userRequest.isDrinker());
            }
            if (userRequest.isSmoker() != existingUser.isSmoker()) {
                existingUser.setSmoker(userRequest.isSmoker());
            }
            if (userRequest.getStartDate() != null) {
                existingUser.setStartDate(userRequest.getStartDate());
            }
            if (userRequest.getEndDate() != null) {
                existingUser.setEndDate(userRequest.getEndDate());
            }
            if (userRequest.getRoomPreference() != null) {
                existingUser.setRoomPreference(userRequest.getRoomPreference());
            }
            if (userRequest.getSearchStatus() != null) {
                existingUser.setSearchStatus(userRequest.getSearchStatus());
            }
            if (userRequest.getYear() != 0) {
                existingUser.setYear(userRequest.getYear());
            }

            userRepo.save(existingUser);

            return ResponseEntity.ok("User profile updated successfully.");
        } else {
            return ResponseEntity.notFound().build(); // User not found
        }

    }

    public ResponseEntity<User> getUser(String username) {
        Optional<User> fetchedUser = userRepo.findByUsername(username);

        if (fetchedUser.isPresent()) {
            return new ResponseEntity<>(fetchedUser.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<List<User>> getUserByUniAndisActive(String university, String excludeUsername, RoommateSearchStatus searchStatus) {

        List<User> fetchedUsers = userRepo.findByUniversityAndSearchStatus(university, searchStatus);

        List<User> filteredUsers = fetchedUsers.stream()
                .filter(user -> !user.getUsername().equals(excludeUsername))
                .toList();

        if (!fetchedUsers.isEmpty()) {
            return new ResponseEntity<>(filteredUsers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}