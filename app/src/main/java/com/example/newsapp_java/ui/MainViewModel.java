package com.example.newsapp_java.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsapp_java.domain.usecases.GetNewsUseCase;
import com.example.newsapp_java.domain.model.Article;
import com.example.newsapp_java.utils.RxSingleSchedulers;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MainViewModel extends ViewModel {

    private final CompositeDisposable disposables = new CompositeDisposable();

    private final GetNewsUseCase getNewsUseCase;
    private final RxSingleSchedulers rxSingleSchedulers;
    public MutableLiveData<List<Article>> articleLiveData = new MutableLiveData<>();
    public MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    @Inject
    public MainViewModel(GetNewsUseCase getNewsUseCase, RxSingleSchedulers rxSingleSchedulers) {
        this.getNewsUseCase = getNewsUseCase;
        this.rxSingleSchedulers = rxSingleSchedulers;
        getNewsList();
    }

    public void getNewsList() {
        disposables.add(getNewsUseCase.getNewsList()
                .compose(rxSingleSchedulers.applySchedulers())
                .subscribe(articles -> {
                            articleLiveData.postValue(articles);
                        },
                        error -> {
                            errorLiveData.postValue("");
                        }));
    }

    @Override
    protected void onCleared() {
        disposables.clear();
        super.onCleared();
    }
}
