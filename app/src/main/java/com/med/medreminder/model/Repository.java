package com.med.medreminder.model;

import android.content.Context;

import com.med.medreminder.db.LocalSource;

public class Repository implements RepositoryInterface{

    private Context context;
    LocalSource localSource;
    private static Repository repository = null;

    public Repository(Context context, LocalSource localSource) {
        this.context = context;
        this.localSource = localSource;
    }

    public static Repository getInstance(Context context, LocalSource localSource) {
        if(repository == null){
            repository = new Repository(context, localSource);
        }
        return repository;
    }


    @Override
    public void insertMedicine(Medicine medicine) {
        localSource.insert(medicine);
    }
}
