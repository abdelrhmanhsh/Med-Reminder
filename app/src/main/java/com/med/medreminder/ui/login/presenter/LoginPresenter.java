package com.med.medreminder.ui.login.presenter;

import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.LifecycleOwner;

import com.med.medreminder.firebase.firebaseLoginDelegate;
import com.med.medreminder.model.RepositoryInterface;
import com.med.medreminder.model.User;
import com.med.medreminder.ui.login.view.loginViewInterface;

public class LoginPresenter implements loginPresenterInterface, firebaseLoginDelegate {

    RepositoryInterface repo;
    loginViewInterface loginViewInterface;

    public LoginPresenter(RepositoryInterface repo, loginViewInterface loginViewInterface) {
        this.repo = repo;
        this.loginViewInterface = loginViewInterface;
    }

    @Override
    public void loginWithGoogle(Context context) {
       repo.loginWithGoogle(context, this);
    }

    @Override
    public void isUserExistInGoogleLogin(String email,User user, String idToken) {
        repo.isUserExistFromGoogle(email,this, user, idToken);
    }

    @Override
    public void addUserToFirestore(LifecycleOwner lifecycleOwner, User user, String idToken, Context context) {
        repo.addUserToFirestore(lifecycleOwner, user,this, idToken, context);
    }

    @Override
    public void authWithGoogle(String email, String idToken, Context context) {
        repo.authWithGoogle(idToken,email,context,this);
    }

    @Override
    public void login(String email, String password, Context context) {
        repo.login(email,password,context,this);
    }

    @Override
    public void intentResultFromGoogleLogin(Intent signInIntent) {
        loginViewInterface.intentResultForGoogleLogin(signInIntent);
    }

    @Override
    public  void userExist(String email,String idToken){
        loginViewInterface.userExist(email,idToken);
    }

    @Override
    public void newUser(User user, String idToken) {
       loginViewInterface.newUser(user,idToken);
    }

    @Override
    public void firebaseAuthWithGoogle(String idToken, String email, Context context) {
        //call method inside repo
        repo.authWithGoogle(idToken,email, context, this);
    }

    @Override
    public void failedToAddUserToFireStore(String msg) {
       loginViewInterface.failedToCreateAccount(msg);
    }

    @Override
    public void authWithGoogleSuccess(String msg) {
        loginViewInterface.authWithGoogleSuccess(msg);
    }

    @Override
    public void authWithGoogleFailed(String msg) {
       loginViewInterface.authWithGoogleFailed(msg);
    }

    @Override
    public void loginSuccessfully(String msg) {
        loginViewInterface.loginSuccessfully(msg);
    }

    @Override
    public void loginFailed(String msg) {
        loginViewInterface.loginFailed(msg);

    }
}
