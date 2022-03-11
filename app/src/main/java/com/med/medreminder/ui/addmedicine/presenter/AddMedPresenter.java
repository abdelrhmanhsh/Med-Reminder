package com.med.medreminder.ui.addmedicine.presenter;

import com.med.medreminder.model.Medicine;
import com.med.medreminder.model.RepositoryInterface;
import com.med.medreminder.ui.addmedicine.view.AddMedViewInterface;

public class AddMedPresenter implements AddMedPresenterInterface{

    AddMedViewInterface view;
    RepositoryInterface repo;

    public AddMedPresenter(AddMedViewInterface view, RepositoryInterface repo){
        this.repo = repo;
        this.view = view;
    }

    @Override
    public void addMed(Medicine medicine) {
        repo.insertMedicine(medicine);
    }

}
