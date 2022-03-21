package com.med.medreminder.ui.signup.view;

import com.med.medreminder.model.User;

public interface signupViewInterface {
    void showToast(String msg);
    void userAlreadyExist(String msg);
    void newUser();
    void signup(String email, String password, User user);
    void signupSuccess(User user);
    void signupFail(String msg);
    void addUserToFirestore(User user);
    void addUserToFirestoreSuccessfully(String msg);
    void addUserToFirestoreFailed(String msg);
}
