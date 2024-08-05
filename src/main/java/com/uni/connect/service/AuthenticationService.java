package com.uni.connect.service;

import com.uni.connect.dao.InvitationRepo;
import com.uni.connect.dao.UidUnameMapRepo;
import com.uni.connect.dao.UserRepo;
import com.uni.connect.exceptions.UserNotFoundException;
import com.uni.connect.model.AuthenticationResponse;
import com.uni.connect.model.Invitations;
import com.uni.connect.model.UidUnameMap;
import com.uni.connect.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthenticationService {
    @Autowired
    InvitationRepo invitationRepo;

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UidUnameMapRepo uidUnameMapRepo;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepo userRepo, PasswordEncoder passwordEncoder, JwtService jwtService, UidUnameMapRepo uidUnameMapRepo, AuthenticationManager authenticationManager) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.uidUnameMapRepo = uidUnameMapRepo;
        this.authenticationManager = authenticationManager;
    }


    public AuthenticationResponse register(User request){

        if (userRepo.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username/email already exists");
        }

        User user = new User();

        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setUid(UUID.randomUUID().toString());

        // Initialize Invitations
        Invitations invitations = new Invitations(user.getUsername());
        invitations.setIncoming(new ArrayList<>());
        invitations.setConnections(new ArrayList<>());
        invitations.setSent(new ArrayList<>());
        invitations.setRejected(new ArrayList<>());
        user.setInvitations(invitations);

        //mapping uid to username
        UidUnameMap uidUname = new UidUnameMap();
        uidUname.setUid(user.getUid());
        uidUname.setUsername(request.getUsername());

        //Save to repo Operations
        user = userRepo.save(user);
        uidUnameMapRepo.save(uidUname);
        invitationRepo.save(invitations);

        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(User request){

        User user = userRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        }catch (AuthenticationException ex) {
                throw new IllegalArgumentException("Bad credentials");
            }

        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token);

    }
}
