package com.uni.connect.dao;

import com.uni.connect.model.Invitations;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InvitationRepo extends MongoRepository<Invitations, String> {
}
