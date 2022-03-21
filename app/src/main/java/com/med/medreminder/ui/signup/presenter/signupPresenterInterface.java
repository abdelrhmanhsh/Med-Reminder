package com.med.medreminder.ui.signup.presenter;

import androidx.lifecycle.LifecycleOwner;

import com.med.medreminder.model.User;

public interface signupPresenterInterface {
    void isUserExist(String email);
    void signup(LifecycleOwner lifecycleOwner, String email, String password, User user);
    void addUserToFirestore(User user);

}
