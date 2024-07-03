package com.uni.connect.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "invitations")
public class Invitations {

    @Id
    private String username;
    private List<String> sent = new ArrayList<>();
    private List<String> incoming = new ArrayList<>();
    private List<String> connections = new ArrayList<>();
    private List<String> rejected = new ArrayList<>();

    public Invitations(String username) {
        this.username = username;
    }
}
