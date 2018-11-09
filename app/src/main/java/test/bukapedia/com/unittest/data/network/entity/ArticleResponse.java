package test.bukapedia.com.unittest.data.network.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticleResponse {
    @SerializedName("source")
    String source;
    @SerializedName("articles")
    List<ArticleEntity> articles;

    public List<ArticleEntity> getArticles() {
        return articles;
    }

    public String getSource(){
        return source;
    }
}
