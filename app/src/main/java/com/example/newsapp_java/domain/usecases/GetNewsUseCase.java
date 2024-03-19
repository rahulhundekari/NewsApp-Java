package com.example.newsapp_java.domain.usecases;

import android.content.Context;

import com.example.newsapp_java.domain.model.Article;
import com.example.newsapp_java.domain.model.News;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetNewsUseCase {

    private final Context context;
    private final Gson gson;

    @Inject
    public GetNewsUseCase(Context context, Gson gson) {
        this.context = context;
        this.gson = gson;
    }

    public Single<List<Article>> getNewsList() {
        return Single.fromCallable(() -> {
            News news = gson.fromJson(loadJSONFromAsset(), News.class);
            Collections.sort(news.getArticles());
            return news.getArticles();
        });


    }

    private String loadJSONFromAsset() throws IOException {
        String json;

        InputStream is = context.getAssets().open("news.json");
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        json = new String(buffer, StandardCharsets.UTF_8);

        return json;
    }
}
