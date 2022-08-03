package com.example.android.selectionscreen;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MainActivityViewModel extends AndroidViewModel {

    MutableLiveData<Float> scaleSelectionValue=new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void setScaleSelectionValue(Float scaleSelectionValue) {
        this.scaleSelectionValue.setValue(scaleSelectionValue);
    }
    public LiveData<Float> getScaleSelectionValue(){
        return this.scaleSelectionValue;
    }
}
