package com.med.medreminder.ui.request.presenter;

import com.med.medreminder.model.RepositoryInterface;

public class RequestPresenter implements RequestPresenterInterface{

    RepositoryInterface repo;

    public RequestPresenter(RepositoryInterface repo){
        this.repo = repo;
    }

    @Override
    public void updateStatusInFirestore(String helperEmail, String patientEmail, String status) {
        repo.updateStatusInFirestore(helperEmail,patientEmail,status);
    }
}
