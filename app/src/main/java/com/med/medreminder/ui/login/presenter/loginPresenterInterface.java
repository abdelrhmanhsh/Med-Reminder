package com.med.medreminder.ui.login.presenter;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;

import com.med.medreminder.firebase.firebaseLoginDelegate;
import com.med.medreminder.model.User;

public interface loginPresenterInterface {
    void loginWithGoogle(Context context);
    void isUserExistInGoogleLogin(String email, User user, String idToken);
   void addUserToFirestore(LifecycleOwner lifecycleOwner, User user, String idToken, Context context);
   void authWithGoogle(String email, String idToken, Context context);
   void login(String email, String password, Context context);
}
