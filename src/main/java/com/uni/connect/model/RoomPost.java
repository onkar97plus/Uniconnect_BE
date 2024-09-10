package com.uni.connect.model;

import com.uni.connect.model.Enums.RoomPreference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "RoomPost")
public class RoomPost {

    @Id
    private String username;
    private String state;
    private String city;
    private String university;
    private RoomPreference roomType;
    private int roomRent;
    private int utilitiesCost;
    private String communityName;
    private String address;
    private LocalDate fromDate;
    private LocalDate toDate;
    private int totalMembers;
    private String additionalInfo;

}
