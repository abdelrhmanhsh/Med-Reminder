package com.med.medreminder.firebase;

import android.content.Intent;

import com.med.medreminder.model.User;

public interface FirebaseDelegate {
     void userExist(String msg);
     void newUser();
     void signupSuccess(User user);
     void signupFail(String msg);
     void userAddedToFirestore(String msg);
     void userFailToFirestore(String msg);
}
