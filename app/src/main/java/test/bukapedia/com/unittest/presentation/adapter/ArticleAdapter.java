package test.bukapedia.com.unittest.presentation.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import test.bukapedia.com.unittest.R;
import test.bukapedia.com.unittest.domain.model.Article;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private List<Article> articles;

    public ArticleAdapter() {
        articles = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(articles.get(i));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void addArticles(List<Article> articles) {
        this.articles.addAll(articles);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView authorTextView;
        private AppCompatTextView titleTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            authorTextView = itemView.findViewById(R.id.tv_author);
            titleTextView = itemView.findViewById(R.id.tv_title);
        }

        public void bind(Article article) {
            authorTextView.setText(article.getAuthor());
            titleTextView.setText(article.getTitle());
        }
    }
}
