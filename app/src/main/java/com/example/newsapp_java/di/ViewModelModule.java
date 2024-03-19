package com.example.newsapp_java.di;

import android.content.Context;

import com.example.newsapp_java.domain.usecases.GetNewsUseCase;
import com.example.newsapp_java.ui.MainViewModel;
import com.example.newsapp_java.utils.RxSingleSchedulers;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;

@Module
@InstallIn(ViewModelComponent.class)
public class ViewModelModule {


    @Provides
    public GetNewsUseCase provideGetNewsUseCase(@ApplicationContext Context context,
                                                Gson gson) {
        return new GetNewsUseCase(context, gson);
    }

    @Provides
    public MainViewModel provideMainViewModel(GetNewsUseCase getNewsUseCase,
                                              RxSingleSchedulers rxSchedulers) {
        return new MainViewModel(getNewsUseCase, rxSchedulers);
    }
}
