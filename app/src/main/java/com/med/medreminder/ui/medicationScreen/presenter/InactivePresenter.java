package com.med.medreminder.ui.medicationScreen.presenter;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.med.medreminder.model.Medicine;
import com.med.medreminder.model.RepositoryInterface;
import com.med.medreminder.ui.medicationScreen.view.InactiveMedViewInterface;

import java.util.Calendar;
import java.util.List;

public class InactivePresenter implements InactivePresenterInterface {

    RepositoryInterface repositoryInterface;
    InactiveMedViewInterface inactiveMedViewInterface;
    Long timeNow = Calendar.getInstance().getTimeInMillis();

    public InactivePresenter( InactiveMedViewInterface inactiveMedViewInterface,RepositoryInterface repositoryInterface) {
        this.repositoryInterface = repositoryInterface;
        this.inactiveMedViewInterface = inactiveMedViewInterface;
    }

    @Override
    public void showInactiveStoredMedicines(LifecycleOwner owner) {
        repositoryInterface.getInactiveMedications(timeNow).observe(owner, new Observer<List<Medicine>>() {
            @Override
            public void onChanged(List<Medicine> medicines) {
                inactiveMedViewInterface.getInactiveMeds(medicines);
            }
        });
    }
}
