package com.med.medreminder.ui.homepage.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.med.medreminder.R;
import com.med.medreminder.databinding.FragmentDashboardBinding;
import com.med.medreminder.databinding.FragmentHomeBinding;
import com.med.medreminder.db.ConcreteLocalSource;
import com.med.medreminder.model.Medicine;
import com.med.medreminder.model.Repository;
import com.med.medreminder.ui.addmedicine.view.AddMedActivity;
import com.med.medreminder.ui.homepage.presenter.HomeMedPresenter;
import com.med.medreminder.ui.homepage.presenter.homeMedPresenterInterface;
import com.med.medreminder.ui.meddisplayedit.view.DisplayEditViewInterface;
import com.med.medreminder.ui.meddisplayedit.view.MedDisplayEditActivity;
import com.med.medreminder.ui.medicationScreen.view.ActiveMedsAdapter;
import com.med.medreminder.ui.medicationScreen.view.InactiveMedsAdapter;
import com.med.medreminder.ui.medicationScreen.view.OnActiveMedClickListener;
import com.med.medreminder.ui.medicationScreen.view.OnInactiveMedClickListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class DashboardFragment extends Fragment implements OnInactiveMedClickListener,
        OnActiveMedClickListener, homeMedViewInterface {

    private RecyclerView activeMeds;
    private RecyclerView inactiveMeds;
    private ActiveMedsAdapter activeAdapter;
    private InactiveMedsAdapter inactiveAdapter;
    private Button medBtn;
    homeMedPresenterInterface homeMedPresenterInterface;
    DisplayEditViewInterface displayEditViewInterface;

    private FragmentDashboardBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activeMeds = view.findViewById(R.id.recyclerView_ActiveMeds);
        inactiveMeds = view.findViewById(R.id.recyclerView_InctiveMeds);
        medBtn = view.findViewById(R.id.medBtn);

        activeMeds.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        activeMeds.setLayoutManager(layoutManager);
        activeAdapter = new ActiveMedsAdapter(this,getContext());
        activeMeds.setAdapter(activeAdapter);

        homeMedPresenterInterface = new HomeMedPresenter(this, Repository.getInstance(getContext(),
                ConcreteLocalSource.getInstance(getContext())));

        Log.d("TAG", "Dashboard Fragment: " + getViewLifecycleOwner());

        homeMedPresenterInterface.showAllStoredMedicines(getViewLifecycleOwner());
        Log.d("TAG", "onViewCreated: " + 1);

        medBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddMedActivity.class));
            }
        });
        Log.d("TAG", "onViewCreated: " + 2);


//        inactiveAdapter = new InactiveMedsAdapter(MedicationsScreen.inactive_meds);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        inactiveMeds.setLayoutManager(layoutManager2);
        inactiveMeds.setAdapter(inactiveAdapter);

    }


    @Override
    public void onCLick(Medicine medicine) {
    }

    @Override
    public void onActiveCLick(Medicine medicine) {
//        Intent intent = new Intent(getActivity(), MedDisplayEditActivity.class);
//        intent.putExtra("id", medicine.getId());
//        startActivity(intent);


//        DashboardFragmentDirections.ActionNavigationDashboardToDisplayEditMedicationGraph action =
//                DashboardFragmentDirections.actionNavigationDashboardToDisplayEditMedicationGraph();
//        action.setId(medicine.getId());
//        Navigation.findNavController(getView()).navigate(action);


        Bundle bundle = new Bundle();
        bundle.putInt("id", medicine.getId());
        bundle.putBoolean("suspended", true);
        NavController navController = Navigation.findNavController(getView());
        navController.navigate(R.id.actionNavigationDashboardToDisplayEditMedicationGraph, bundle);


//        NavDirections action = DashboardFragmentDirections.actionNavigationDashboardToDisplayEditMedicationGraph();
//        Navigation.findNavController(getView()).navigate(action);

    }

    @Override
    public void getAllStoredMedicines(List<Medicine> medicines) {
        activeAdapter.setMedInfo(medicines);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}