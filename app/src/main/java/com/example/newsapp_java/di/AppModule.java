package com.example.newsapp_java.di;

import android.content.Context;

import com.example.newsapp_java.utils.RxSingleSchedulers;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    Context provideContext(@ApplicationContext Context context) {
        return context;
    }

    @Provides
    RxSingleSchedulers provideRxSchedulers() {
        return RxSingleSchedulers.DEFAULT;
    }
}
