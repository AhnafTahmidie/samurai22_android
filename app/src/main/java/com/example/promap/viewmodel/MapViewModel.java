package com.example.promap.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.promap.model.Projects;

import java.util.List;


public class MapViewModel extends AndroidViewModel {
    public MutableLiveData<List<Projects>> projects = new MutableLiveData<>();
    public MapViewModel(@NonNull Application application) {
        super(application);
    }

    public void getData(){

    }
}
