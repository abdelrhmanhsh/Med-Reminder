package com.med.medreminder.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.med.medreminder.model.Medicine;

@Database(entities = {Medicine.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance = null;

    public abstract MedicineDao medicineDao();

    public static synchronized AppDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "medicines")
                    .build();
        }
        return instance;
    }

}
