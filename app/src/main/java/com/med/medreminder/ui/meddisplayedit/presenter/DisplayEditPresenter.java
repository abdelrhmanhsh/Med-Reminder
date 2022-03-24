package com.med.medreminder.ui.meddisplayedit.presenter;

import androidx.lifecycle.LiveData;

import com.med.medreminder.firebase.FirebaseGetMedDelegate;
import com.med.medreminder.model.Medicine;
import com.med.medreminder.model.RepositoryInterface;
import com.med.medreminder.ui.meddisplayedit.view.DisplayEditViewInterface;

public class DisplayEditPresenter implements DisplayPresenterInterface, FirebaseGetMedDelegate {

    DisplayEditViewInterface view;
    RepositoryInterface repo;

    public DisplayEditPresenter(DisplayEditViewInterface view, RepositoryInterface repo){
        this.repo = repo;
        this.view = view;
    }

    @Override
    public LiveData<Medicine> getMedDetails(long id) {
        return repo.getMedicineById(id);
    }

    @Override
    public void updateMed(Medicine medicine) {
        repo.updateMedicine(medicine);
    }

    @Override
    public void getMedByIdFirestore(String email, long id) {
        repo.getMedFromFirestoreById(email, id, this);
    }

    @Override
    public void deleteMed(Medicine medicine) {
        repo.deleteMedicine(medicine);
    }

    @Override
    public void updateMedFirestore(Medicine medicine, String email, long id) {
        repo.updateMedFirestore(medicine, email, id);
    }

    @Override
    public void deleteMedFirestore(String email, long id) {
        repo.deleteMedFirestore(email, id);
    }

    @Override
    public void onSuccess(Medicine medicine) {
        view.getMedByIdFirestore(medicine);
    }

    @Override
    public void onFailure(String msg) {

    }

}
