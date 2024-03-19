package com.example.newsapp_java.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;

import com.example.newsapp_java.domain.usecases.GetNewsUseCase;
import com.example.newsapp_java.domain.model.Article;
import com.example.newsapp_java.utils.RxSingleSchedulers;
import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

@RunWith(MockitoJUnitRunner.Silent.class)
public class MainViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
    @Rule
    public MockitoRule rule = MockitoJUnit.rule().silent();

    @Mock
    Context context;
    Gson mockGson;

    private GetNewsUseCase getNewsUseCase;

    private MainViewModel viewModel;
    @Mock
    Observer<List<Article>> observer;
    @Mock
    LifecycleOwner lifecycleOwner;
    Lifecycle lifecycle;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        lifecycle = new LifecycleRegistry(lifecycleOwner);
        mockGson = new Gson();
        getNewsUseCase = new GetNewsUseCase(context, mockGson);
        viewModel = new MainViewModel(getNewsUseCase, RxSingleSchedulers.TEST_SCHEDULER);
        viewModel.articleLiveData.observeForever(observer);
    }

    @Test
    public void checkNulls() {
        when(getNewsUseCase.getNewsList()).thenReturn(null);
        assertNotNull(viewModel.articleLiveData);
        assertTrue(viewModel.articleLiveData.hasObservers());
    }

    @Test
    public void testApiFetchDataSuccess() {
        try (MockedConstruction<GetNewsUseCase> mockPaymentService =
                     Mockito.mockConstruction(GetNewsUseCase.class, (mock, context) -> {
                         when(mock.getNewsList()).thenReturn(Single.just(new ArrayList<>()));
                         verify(mock).getNewsList();
                         viewModel.getNewsList();
                         verify(observer).onChanged(viewModel.articleLiveData.getValue());
                     })) {
            assertEquals(0, mockPaymentService.constructed().size());
        }
    }

    @Test
    public void testApiFetchDataError() {
        try (MockedConstruction<GetNewsUseCase> mockPaymentService =
                     Mockito.mockConstruction(GetNewsUseCase.class, (mock, context) -> {
                         when(mock.getNewsList()).thenReturn(Single.error(new Throwable("Error")));
                         verify(mock).getNewsList();
                         viewModel.getNewsList();
                         assertEquals("Error", viewModel.errorLiveData.getValue());
                     })) {
            assertEquals(0, mockPaymentService.constructed().size());
        }
    }

    @After
    public void tearDown() throws Exception {
        getNewsUseCase = null;
        viewModel = null;
    }
}