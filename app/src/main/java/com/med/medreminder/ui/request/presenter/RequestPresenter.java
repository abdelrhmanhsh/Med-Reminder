package com.med.medreminder.ui.request.presenter;

import com.med.medreminder.firebase.FirebaseLoadRequestsDelegate;
import com.med.medreminder.model.RepositoryInterface;
import com.med.medreminder.ui.request.view.RequestViewInterface;

import java.util.List;

public class RequestPresenter implements RequestPresenterInterface, FirebaseLoadRequestsDelegate {

    RepositoryInterface repo;
    RequestViewInterface requestViewInterface;

    public RequestPresenter(RequestViewInterface requestViewInterface,RepositoryInterface repo){
        this.repo = repo;
        this.requestViewInterface = requestViewInterface;
    }

    @Override
    public void updateStatusInFirestore(String helperEmail, String patientEmail, String status) {
        repo.updateStatusInFirestore(helperEmail,patientEmail,status);
    }

    @Override
    public void addHelperToFirestore(String helperEmail, String patientEmail) {
        repo.addHelperToFirestore(helperEmail,patientEmail);
    }

    @Override
    public void loadRequests(String myEmail) {
        repo.loadRequests(myEmail,this);
    }

    @Override
    public void successToLoadRequests(List<String> helper_email) {
        requestViewInterface.successToLoadRequests(helper_email);
    }

    @Override
    public void failedToLoadRequests(String msg) {
        requestViewInterface.failedToLoadRequests(msg);
    }
}
