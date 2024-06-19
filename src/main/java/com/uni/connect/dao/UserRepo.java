package com.uni.connect.dao;

import com.uni.connect.model.Enums.RoommateSearchStatus;
import com.uni.connect.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepo extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);
    //Optional<User> findByEmail(String email);

    List<User> findByUniversity(String university);

    List<User> findByUniversityAndSearchStatus(String university, RoommateSearchStatus searchStatus);
}
