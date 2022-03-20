package com.med.medreminder.ui.addmedicine.presenter;

import android.widget.Toast;

import com.med.medreminder.firebase.firebaseDelegate;
import com.med.medreminder.model.Medicine;
import com.med.medreminder.model.RepositoryInterface;
import com.med.medreminder.ui.addmedicine.view.AddMedViewInterface;

public class AddMedPresenter implements AddMedPresenterInterface {

    RepositoryInterface repo;

    public AddMedPresenter(RepositoryInterface repo){
        this.repo = repo;
    }

    @Override
    public void addMed(Medicine medicine) {
        repo.insertMedicine(medicine);
    }

    @Override
    public void addMedToFirestore(Medicine medicine, String email, long id) {
        repo.addMedToFirestore(medicine, email, id);
    }

//    @Override
//    public void onSuccessResult(String successMsg) {
//        view.showToast(successMsg);
//    }
//
//    @Override
//    public void onFailureResult(String errorMsg) {
//        view.showToast(errorMsg);
//    }
}
