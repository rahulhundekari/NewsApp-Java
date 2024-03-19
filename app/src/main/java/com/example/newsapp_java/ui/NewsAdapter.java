package com.example.newsapp_java.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp_java.R;
import com.example.newsapp_java.domain.model.Article;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<Article> newsList;

    public NewsAdapter(List<Article> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article article = newsList.get(position);

        holder.title.setText(article.getTitle());
        holder.published_date.setText(article.getPublishedAt());
        Glide.with(holder.article_image.getContext())
                .load(article.getUrlToImage())
                .override(200, 200)
                .into(holder.article_image);

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, published_date;
        ImageView article_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            published_date = itemView.findViewById(R.id.published_date);
            article_image = itemView.findViewById(R.id.article_image);
        }
    }

    public void updateList(List<Article> newsList) {
        this.newsList = newsList;
        notifyDataSetChanged();
    }
}
