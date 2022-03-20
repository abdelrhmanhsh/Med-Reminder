package com.med.medreminder.firebase;

import android.content.Context;
import android.content.Intent;

import com.med.medreminder.model.User;

public interface firebaseLoginDelegate {
    void intentResultFromGoogleLogin(Intent signInIntent);
    void userExist(String email,String idToken);
    void newUser(User user, String idToken);
    void firebaseAuthWithGoogle(String idToken, String email, Context context);
    void failedToAddUserToFireStore(String msg);
    void authWithGoogleSuccess(String msg);
    void authWithGoogleFailed(String msg);
    void loginSuccessfully(String msg);
    void loginFailed(String msg);

}
