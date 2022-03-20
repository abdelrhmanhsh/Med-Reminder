package com.med.medreminder.ui.signup.presenter;

import com.med.medreminder.firebase.firebaseDelegate;
import com.med.medreminder.model.User;

public interface signupPresenterInterface {
    void isUserExist(String email);
    void signup(String email, String password, User user);
    void addUserToFirestore(User user);

}
