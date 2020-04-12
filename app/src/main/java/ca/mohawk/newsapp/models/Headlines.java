package ca.mohawk.newsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ca.mohawk.newsapp.models.Articles;

/**
 *
 */
public class Headlines {


    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("totalResults")
    @Expose
    private String totalResults;

    @SerializedName("articles")
    @Expose
    private List<Articles> articles;

    /**
     *
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @return
     */
    public String getTotalResults() {
        return totalResults;
    }

    /**
     *
     * @return
     */
    public List<Articles> getArticles() {
        return articles;
    }
}
