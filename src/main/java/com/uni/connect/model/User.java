package com.uni.connect.model;

import com.uni.connect.model.Enums.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Document(collection = "users")
public class User implements UserDetails {

    private String firstName;
    private String lastName;
    private String password;
    @Id
    private String username; //email
    private Role role;
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

    @DBRef
    private Invitations invitations;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setInvitations(Invitations invitations) {
        this.invitations = invitations;
        this.invitations.setUsername(this.username);
    }
}
