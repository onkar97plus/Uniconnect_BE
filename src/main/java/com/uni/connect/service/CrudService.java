package com.uni.connect.service;

import com.uni.connect.dao.InvitationRepo;
import com.uni.connect.dao.UserRepo;
import com.uni.connect.model.Enums.RoommateSearchStatus;
import com.uni.connect.model.Invitations;
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

    @Autowired
    InvitationRepo invitationRepo;


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

    public ResponseEntity<String> sendInvitations(String username, String sendReqToUser) {

        Optional<User> fetchedUser = userRepo.findByUsername(username);
        Optional<User> fetchedUser2 = userRepo.findByUsername(sendReqToUser);

        if (fetchedUser.isPresent() && fetchedUser2.isPresent()) {

            User user = fetchedUser.get();
            User user1 = fetchedUser2.get();

            // Initialize Invitations if null and save it to the database
            if (user.getInvitations() == null) {
                Invitations invitations = new Invitations(user.getUsername());
                user.setInvitations(invitations);
                invitationRepo.save(invitations);
            }
            if (user1.getInvitations() == null) {
                Invitations invitations1 = new Invitations(user1.getUsername());
                user1.setInvitations(invitations1);
                invitationRepo.save(invitations1);
            }

            Invitations invi = user.getInvitations();
            Invitations invi1 = user1.getInvitations();

            List<String> sentInvitations = invi.getSent();
            List<String> incomingInvitations = invi1.getIncoming();

            if (sentInvitations == null) {
                sentInvitations = new ArrayList<>();
                invi.setSent(sentInvitations);
            }
            if (incomingInvitations == null) {
                incomingInvitations = new ArrayList<>();
                invi1.setIncoming(incomingInvitations);
            }

            sentInvitations.add(sendReqToUser);
            incomingInvitations.add(username);

            invitationRepo.save(invi);
            invitationRepo.save(invi1);
            userRepo.save(user);
            userRepo.save(user1);

            return ResponseEntity.ok("Invitation sent.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // unable to save
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

    public ResponseEntity<List<String>> getSentInvitations(String username) {
        Optional<User> fetchedUser = userRepo.findByUsername(username);
        if (fetchedUser.isPresent()) {
            User user = fetchedUser.get();
            //List<String> sentInvitations = user.getInvitations().getSent();
            return new ResponseEntity<>(user.getInvitations().getSent(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*public ResponseEntity<List<User>> getUserByUni(String university) {

        List<User> fetchedUsers = userRepo.findByUniversity(university);

        if (!fetchedUsers.isEmpty()){
            return new ResponseEntity<>(fetchedUsers, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/

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

    public ResponseEntity<String> acceptInvitation(String username, String acceptUser) {
        Optional<User> fetchedUser = userRepo.findByUsername(username);
        Optional<User> fetchedUser1 = userRepo.findByUsername(acceptUser);

        Invitations invitations = fetchedUser.get().getInvitations();
        Invitations invitations1 = fetchedUser1.get().getInvitations();

        if (fetchedUser.isPresent() && fetchedUser1.isPresent()) {
            User user = fetchedUser.get();
            User user1 = fetchedUser1.get();

            List<String> removeFromList = user.getInvitations().getIncoming();
            List<String> removeFromList1 = user1.getInvitations().getSent();
            removeFromList.remove(acceptUser);
            removeFromList1.remove(username);
            invitations.setIncoming(removeFromList);
            invitations1.setSent(removeFromList1);

            List<String> addToList = user.getInvitations().getConnections();
            List<String> addToList1 = user1.getInvitations().getConnections();
            addToList.add(acceptUser);
            addToList1.add(username);
            invitations.setConnections(addToList);
            invitations1.setConnections(addToList1);

            invitationRepo.save(invitations);
            invitationRepo.save(invitations1);

            return new ResponseEntity<>("New Connection",HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}