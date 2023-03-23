package com.chess.registration;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
// @Table(name="chess")
public class Players {
    @Id
    // @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    // @Column
    @NotBlank
    String firstName;
    // @Column
    @NotBlank
    String lastName;
    // @Column
    String emailAddress;
    // @Column
    String personalCode;
    // @Column
    String dateStarted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPersonalCode() {
        return personalCode;
    }

    public void setPersonalCode(String personalCode) {
        this.personalCode = personalCode;
    }

    public String getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(String dateStarted) {
        this.dateStarted = dateStarted;
    }
}
