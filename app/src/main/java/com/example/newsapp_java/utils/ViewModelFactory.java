package com.example.newsapp_java.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.newsapp_java.ui.MainViewModel;

import javax.inject.Inject;
import javax.inject.Provider;


public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Provider<MainViewModel> mainViewModelProvider;

    @Inject
    public ViewModelFactory(Provider<MainViewModel> mainViewModelProvider) {
        this.mainViewModelProvider = mainViewModelProvider;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) mainViewModelProvider.get();
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
