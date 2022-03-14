package com.med.medreminder.model;

public class User {
   private String firstName;
   private String secondName;
   private String gender;
   private String dob;
   private String email;
   private String password;
   private String userUId;

    public User() {
    }

    public User(String firstName, String secondName, String gender, String dob, String email, String password, String userUId) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.gender = gender;
        this.dob = dob;
        this.email = email;
        this.password = password;
        this.userUId = userUId;
    }

    public String getUserUId() {
        return userUId;
    }

    public void setUserUId(String userUId) {
        this.userUId = userUId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getGender() {
        return gender;
    }

    public String getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
