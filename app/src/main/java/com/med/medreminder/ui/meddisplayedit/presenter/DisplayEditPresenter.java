package com.med.medreminder.ui.meddisplayedit.presenter;

import com.med.medreminder.model.Medicine;
import com.med.medreminder.model.RepositoryInterface;
import com.med.medreminder.ui.meddisplayedit.view.DisplayEditViewInterface;

public class DisplayEditPresenter implements DisplayEditPresenterInterface{

    DisplayEditViewInterface view;
    RepositoryInterface repo;

    public DisplayEditPresenter(DisplayEditViewInterface view, RepositoryInterface repo){
        this.repo = repo;
        this.view = view;
    }

    @Override
    public void getMedDetails(Medicine medicine) {

    }
}
