package com.example.newsapp_java.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.newsapp_java.R;
import com.example.newsapp_java.databinding.ActivityMainBinding;
import com.example.newsapp_java.domain.model.Article;
import com.example.newsapp_java.utils.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Inject
    ViewModelFactory viewModelFactory;

    private MainViewModel viewModel;

    private ActivityMainBinding binding;
    private NewsAdapter newsAdapter;
    private List<Article> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this, viewModelFactory).get(MainViewModel.class);

        binding.newsListView.setLayoutManager(new LinearLayoutManager(this));
        binding.newsListView.setHasFixedSize(true);

        newsList = new ArrayList<>();
        newsAdapter = new NewsAdapter(newsList);
        binding.newsListView.setAdapter(newsAdapter);

        setSupportActionBar(binding.toolbar);

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

        bindViewModel();
    }

    private void bindViewModel() {
        viewModel.articleLiveData.observe(this, articleList -> {
            if (articleList.size() > 1) {
                newsList = articleList;
            } else {
                newsList.clear();
            }
            newsAdapter.updateList(newsList);
        });
    }

    private void filter(String text) {
        List<Article> filteredList = new ArrayList<>();

        for (Article article : newsList) {
            if (article.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(article);
            }
        }

        newsAdapter.updateList(filteredList);

    }
}