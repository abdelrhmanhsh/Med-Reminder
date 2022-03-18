package com.med.medreminder.ui.medfriend.presenter;

import com.med.medreminder.model.RepositoryInterface;

public class MedFriendPresenter implements MedFriendPresenterInterface{

    RepositoryInterface repo;

    public MedFriendPresenter(RepositoryInterface repo) {
        this.repo = repo;
    }

    @Override
    public void addRequestsToFirestore(String email, String name, String status, String helper_email) {
        repo.addRequestsToFirestore(email,name,status,helper_email);
    }
}
