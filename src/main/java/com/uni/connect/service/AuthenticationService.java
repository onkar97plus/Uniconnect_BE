package com.uni.connect.service;

import com.uni.connect.dao.UidUnameMapRepo;
import com.uni.connect.dao.UserRepo;
import com.uni.connect.exceptions.UserNotFoundException;
import com.uni.connect.model.AuthenticationResponse;
import com.uni.connect.model.UidUnameMap;
import com.uni.connect.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class AuthenticationService {

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

        //mapping uid to username
        UidUnameMap uidUname = new UidUnameMap();
        uidUname.setUid(user.getUid());
        uidUname.setUsername(request.getUsername());

        //Save to repo Operations
        user = userRepo.save(user);
        uidUnameMapRepo.save(uidUname);

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

        //User user = userRepo.findByUsername(request.getUsername()).orElseThrow(() -> new IllegalArgumentException("User not found"));

        String token = jwtService.generateToken(user);



        return new AuthenticationResponse(token);

    }
}
