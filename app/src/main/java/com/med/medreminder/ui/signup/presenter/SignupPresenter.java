package com.med.medreminder.ui.signup.presenter;

import androidx.lifecycle.LifecycleOwner;

import com.med.medreminder.firebase.FirebaseDelegate;
import com.med.medreminder.model.RepositoryInterface;
import com.med.medreminder.model.User;
import com.med.medreminder.ui.signup.view.signupViewInterface;

public class SignupPresenter implements signupPresenterInterface, FirebaseDelegate {

    RepositoryInterface repo;
    signupViewInterface signupViewInterface;

    public SignupPresenter(RepositoryInterface repo, signupViewInterface signupViewInterface) {
        this.repo = repo;
        this.signupViewInterface = signupViewInterface;
    }

    @Override
    public void isUserExist(String email) {
        repo.isUserExist(email, this);
    }

    @Override
    public void signup(LifecycleOwner lifecycleOwner, String email, String password, User user) {
        repo.signup(lifecycleOwner, email,password,this,user);
    }

    @Override
    public void addUserToFirestore(User user) {
      repo.addUserToFirestore(user,this);
    }

    @Override
    public void userExist(String msg) {
         signupViewInterface.userAlreadyExist(msg);
    }

    @Override
    public void newUser() {    //replace userExist with newUser()
      signupViewInterface.newUser();
    }

    @Override
    public void signupSuccess(User user) {
        signupViewInterface.signupSuccess(user);
    }

    @Override
    public void signupFail(String msg) {
        signupViewInterface.signupFail(msg);  //replace it with signupFail
    }

    @Override
    public void userAddedToFirestore(String msg) {
        signupViewInterface.addUserToFirestoreSuccessfully(msg);
    }

    @Override
    public void userFailToFirestore(String msg) {
        signupViewInterface.addUserToFirestoreFailed(msg);

    }
}
