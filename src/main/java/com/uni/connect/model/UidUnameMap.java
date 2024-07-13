package com.uni.connect.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "UidUnameMap")
public class UidUnameMap {

    @Id
    private String uid;
    private String username;
}
