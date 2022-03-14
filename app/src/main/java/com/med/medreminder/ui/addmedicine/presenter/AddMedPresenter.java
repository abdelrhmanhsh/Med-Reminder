package com.med.medreminder.ui.addmedicine.presenter;

import com.med.medreminder.model.Medicine;
import com.med.medreminder.model.RepositoryInterface;

public class AddMedPresenter implements AddMedPresenterInterface{

    RepositoryInterface repo;

    public AddMedPresenter(RepositoryInterface repo){
        this.repo = repo;
    }

    @Override
    public void addMed(Medicine medicine) {
        repo.insertMedicine(medicine);
    }

}
