package com.med.medreminder.ui.homepage.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.med.medreminder.R;
import com.med.medreminder.databinding.FragmentDashboardBinding;
import com.med.medreminder.db.ConcreteLocalSource;
import com.med.medreminder.firebase.FirebaseWork;
import com.med.medreminder.model.Medicine;
import com.med.medreminder.model.Repository;
import com.med.medreminder.ui.addmedicine.view.AddMedActivity;
import com.med.medreminder.ui.medfriend.view.MedFriendActivity;
import com.med.medreminder.ui.medicationScreen.presenter.InactivePresenter;
import com.med.medreminder.ui.medicationScreen.presenter.InactivePresenterInterface;
import com.med.medreminder.ui.medicationScreen.presenter.ActivePresenter;
import com.med.medreminder.ui.medicationScreen.presenter.ActivePresenterInterface;
import com.med.medreminder.ui.medicationScreen.view.ActiveMedViewInterface;
import com.med.medreminder.ui.medicationScreen.view.ActiveMedsAdapter;
import com.med.medreminder.ui.medicationScreen.view.InactiveMedViewInterface;
import com.med.medreminder.ui.medicationScreen.view.InactiveMedsAdapter;
import com.med.medreminder.ui.medicationScreen.view.OnActiveMedClickListener;
import com.med.medreminder.ui.medicationScreen.view.OnInactiveMedClickListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DashboardFragment extends Fragment implements OnInactiveMedClickListener, OnActiveMedClickListener, ActiveMedViewInterface, InactiveMedViewInterface {

    private RecyclerView activeMeds;
    private RecyclerView inactiveMeds;
    private ActiveMedsAdapter activeAdapter;
    private InactiveMedsAdapter inactiveAdapter;
    private Button medBtn;
    ActivePresenterInterface activePresenterInterface;
    InactivePresenterInterface inactivePresenterInterface;
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


        inactiveMeds.setHasFixedSize(true);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        inactiveMeds.setLayoutManager(layoutManager2);
        inactiveAdapter = new InactiveMedsAdapter(this,getContext());
        inactiveMeds.setAdapter(inactiveAdapter);

        activePresenterInterface = new ActivePresenter(this, Repository.getInstance(getContext(),
                ConcreteLocalSource.getInstance(getContext()), FirebaseWork.getInstance()));

        Log.d("TAG", "Dashboard Fragment: " + getViewLifecycleOwner());

        activePresenterInterface.showActiveStoredMedicines(getViewLifecycleOwner());

        Log.d("TAG", "onViewCreated: " + 1);

        inactivePresenterInterface = new InactivePresenter(this,Repository.getInstance(getContext(),
                ConcreteLocalSource.getInstance(getContext()), FirebaseWork.getInstance()));
        inactivePresenterInterface.showInactiveStoredMedicines(getViewLifecycleOwner());

        medBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(getActivity(), AddMedActivity.class));
                startActivity(new Intent(getActivity(), MedFriendActivity.class));
            }
        });
        Log.d("TAG", "onViewCreated: " + 2);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void getActiveMeds(List<Medicine> medicines) {

        if(medicines.size()==0){
            //binding.textView.setVisibility(View.GONE);
            //binding.recyclerViewActiveMeds.setVisibility(View.GONE);
        }else{
           // binding.textView.setVisibility(View.VISIBLE);
            //binding.recyclerViewActiveMeds.setVisibility(View.VISIBLE);
           // activeAdapter = new ActiveMedsAdapter(this,getContext());
           // binding.recyclerViewActiveMeds.setAdapter(activeAdapter);
            activeAdapter.setMedInfo(medicines);
        }
    }

    @Override
    public void getInactiveMeds(List<Medicine> medicines) {
        inactiveAdapter.setInactiveMedInfo(medicines);
    }

    @Override
    public void onActiveCLick(Medicine medicine) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", medicine.getId());
        bundle.putBoolean("suspended", false);
        NavController navController = Navigation.findNavController(getView());
        navController.navigate(R.id.actionNavigationDashboardToDisplayEditMedicationGraph, bundle);
    }

    @Override
    public void onInactiveClick(Medicine medicine) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", medicine.getId());
        bundle.putBoolean("suspended", true);
        NavController navController = Navigation.findNavController(getView());
        navController.navigate(R.id.actionNavigationDashboardToDisplayEditMedicationGraph, bundle);
    }
}