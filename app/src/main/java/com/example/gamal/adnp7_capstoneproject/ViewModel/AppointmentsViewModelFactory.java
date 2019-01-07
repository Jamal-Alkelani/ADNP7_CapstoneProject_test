package com.example.gamal.adnp7_capstoneproject.ViewModel;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.gamal.adnp7_capstoneproject.Database.AppDatabase;

public class AppointmentsViewModelFactory extends ViewModelProvider.NewInstanceFactory implements LifecycleObserver {

    private final AppDatabase mDb;
    public static LifecycleOwner lifecycleOwner;

    public AppointmentsViewModelFactory(AppDatabase mDb) {
        this.mDb = mDb;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AppointmetsViewModel(mDb);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStartListener(LifecycleOwner owner){
        lifecycleOwner=owner;
    }

    public LifecycleOwner getLifeCycleOwner() {
        return lifecycleOwner;
    }
}
