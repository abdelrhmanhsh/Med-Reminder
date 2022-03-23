package com.med.medreminder.ui.displayHelpers.presenter;

import com.med.medreminder.firebase.FirebaseHelpersDelegate;
import com.med.medreminder.model.RepositoryInterface;
import com.med.medreminder.ui.displayHelpers.view.HelperViewInterface;

import java.util.List;

public class HelperPresenter implements HelperPresenterInterface, FirebaseHelpersDelegate {

    HelperViewInterface helperViewInterface;
    RepositoryInterface repo;

    public HelperPresenter(HelperViewInterface helperViewInterface,RepositoryInterface repo) {
        this.repo = repo;
        this.helperViewInterface = helperViewInterface;
    }

    @Override
    public void Helpers(String myEmail) {
        repo.Helpers(myEmail, this);
    }



    @Override
    public void successToDisplayRequests(List<String> helper_email) {
        helperViewInterface.successToDisplayRequests(helper_email);
    }

    @Override
    public void failedToDisplayRequests(String msg) {
        helperViewInterface.failedToDisplayRequests(msg);
    }
}
