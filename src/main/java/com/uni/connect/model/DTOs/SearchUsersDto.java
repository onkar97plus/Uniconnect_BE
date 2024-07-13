package com.uni.connect.model.DTOs;

import com.uni.connect.model.Enums.*;
import com.uni.connect.model.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class SearchUsersDto {

    private String firstName;
    private String lastName;
    private int age;
    private String university;
    private int year;
    private SemesterIntake intakeBatch;
    private Ethnicity ethnicity;
    private Religion religion;
    private EatingPreference eatingPreference;
    private boolean smoker;
    private boolean drinker;
    private String additionalInformation;
    private IndianState state;
    private RoommateSearchStatus searchStatus;
    private LocalDate startDate;
    private LocalDate endDate;
    private RoomPreference roomPreference;
    private String uid;

}
