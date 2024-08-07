package com.uni.connect.service;

import com.uni.connect.dao.InvitationRepo;
import com.uni.connect.dao.UidUnameMapRepo;
import com.uni.connect.dao.UserRepo;
import com.uni.connect.model.Invitations;
import com.uni.connect.model.UidUnameMap;
import com.uni.connect.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvitationService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    InvitationRepo invitationRepo;

    @Autowired
    UidUnameMapRepo uidUnameMapRepo;

    public ResponseEntity<String> sendInvitations(String username, String sendReqToUserUid) {

        UidUnameMap byUid = uidUnameMapRepo.findByUid(sendReqToUserUid);

        String sendReqToUser = byUid.getUsername();


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

    public ResponseEntity<List<User>> getSentInvitations(String username) {
        Optional<User> fetchedUser = userRepo.findByUsername(username);
        if (fetchedUser.isPresent()) {
            User user = fetchedUser.get();
            List<String> sentInvitations = user.getInvitations().getSent();
            List<User> listOfSentUsers = new ArrayList<>();
            for (String sentToUser : sentInvitations) {
                Optional<User> sentToUserObj = userRepo.findByUsername(sentToUser);
                listOfSentUsers.add(sentToUserObj.get());
            }
            return new ResponseEntity<>(listOfSentUsers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> acceptInvitation(String username, String acceptUserUid) {

        UidUnameMap byUid = uidUnameMapRepo.findByUid(acceptUserUid);

        String acceptUser = byUid.getUsername();


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


    public ResponseEntity<List<User>> getIncomingInvitations(String username) {

        Optional<User> fetchedUser = userRepo.findByUsername(username);
        if (fetchedUser.isPresent()) {
            User user = fetchedUser.get();
            if (user.getInvitations()==null){
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.OK);
            }
            List<String> incomingInvitations = user.getInvitations().getIncoming();
            List<User> listOfIncomingUsers = new ArrayList<>();
            for (String incoming : incomingInvitations) {
                Optional<User> sentToUserObj = userRepo.findByUsername(incoming);
                listOfIncomingUsers.add(sentToUserObj.get());
            }
            return new ResponseEntity<>(listOfIncomingUsers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<User>> fetchConnections(String username) {

        Optional<User> fetchedUser = userRepo.findByUsername(username);
        if (fetchedUser.isPresent()) {
            User user = fetchedUser.get();
            List<String> connections = user.getInvitations().getConnections();
            List<User> listOfConnections = new ArrayList<>();
            for (String cons : connections) {
                Optional<User> connectedUser = userRepo.findByUsername(cons);
                listOfConnections.add(connectedUser.get());
            }
            return new ResponseEntity<>(listOfConnections, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public int connectionPolling(String username){

        Optional<User> fetchedUser = userRepo.findByUsername(username);
        int totalIncomingConnections = 0;
        if (fetchedUser.isPresent()) {
            User user = fetchedUser.get();
            if (user.getInvitations()==null){
                return 0;
            }
            //user.getInvitations().getNumberOfLastIncomingRequests();
            totalIncomingConnections = user.getInvitations().getIncoming().size();
        }
        return totalIncomingConnections;
    }

    public void saveRequestsOnLogout(String username) {

        Optional<User> fetchedUser = userRepo.findByUsername(username);
        if (fetchedUser.isPresent()) {
            User user = fetchedUser.get();

            if (user.getInvitations()==null){
                return;
            }

            if(user.getInvitations().getIncoming().isEmpty()){
                return;
            }
            int numberOfLastIncomingRequests = user.getInvitations().getIncoming().size();
            Invitations invitations = user.getInvitations();
            invitations.setNumberOfLastIncomingRequests(numberOfLastIncomingRequests);
            invitationRepo.save(invitations);
        }
    }

    public int getNewRequestsOnLogin(String username) {

        Optional<User> fetchedUser = userRepo.findByUsername(username);
        if (fetchedUser.isPresent()) {
            User user = fetchedUser.get();

            if (user.getInvitations()==null){
                return 0;
            }

            if(user.getInvitations().getIncoming().isEmpty()){
                return 0;
            }
            return user.getInvitations().getIncoming().size()-user.getInvitations().getNumberOfLastIncomingRequests();
        }
        return 0;
    }
}
