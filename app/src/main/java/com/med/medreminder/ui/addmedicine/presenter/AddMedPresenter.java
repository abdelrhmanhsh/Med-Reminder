package com.med.medreminder.ui.addmedicine.presenter;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

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

    @Override
    public void addMedToFirestore(Medicine medicine, String email) {
        repo.addMedToFirestore(medicine, email);
    }

}
