package com.med.medreminder.ui.login.view;

import android.content.Intent;

import com.med.medreminder.model.User;

public interface loginViewInterface {
    void loginWithGoogle();
    void intentResultForGoogleLogin(Intent loginIntent);
    void userExist(String email,String idToken);
    void newUser(User user, String idToken);
    void failedToCreateAccount(String msg);
    void authWithGoogleSuccess(String msg);
    void authWithGoogleFailed(String msg);
    void loginSuccessfully(String msg);
    void loginFailed(String msg);
}
