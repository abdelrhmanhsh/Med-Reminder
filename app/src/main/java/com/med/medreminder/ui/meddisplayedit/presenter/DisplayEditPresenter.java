package com.med.medreminder.ui.meddisplayedit.presenter;

import androidx.lifecycle.LiveData;

import com.med.medreminder.model.Medicine;
import com.med.medreminder.model.RepositoryInterface;
import com.med.medreminder.ui.meddisplayedit.view.DisplayEditViewInterface;

public class DisplayEditPresenter implements DisplayEditPresenterInterface {

    DisplayEditViewInterface view;
    RepositoryInterface repo;

    public DisplayEditPresenter(DisplayEditViewInterface view, RepositoryInterface repo){
        this.repo = repo;
        this.view = view;
    }

    @Override
    public LiveData<Medicine> getMedDetails(int id) {
        return repo.getMedicineById(id);
    }

    @Override
    public void updateMed(Medicine medicine) {
        repo.updateMedicine(medicine);
    }

    @Override
    public void deleteMed(Medicine medicine) {
        repo.deleteMedicine(medicine);
    }

}
