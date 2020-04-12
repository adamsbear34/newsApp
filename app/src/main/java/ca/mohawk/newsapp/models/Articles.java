package ca.mohawk.newsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 */
public class Articles {

    @SerializedName("source")
    @Expose
    private ca.mohawk.newsapp.models.Source source;

    @SerializedName("author")
    @Expose
    private String author;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("urlToImage")
    @Expose
    private String urlToImage;

    @SerializedName("publishedAt")
    @Expose
    private String publishedAt;

    private boolean isAddedToFav;


    /**
     *
     * @return
     */
    public Source getSource() {
        return source;
    }

    /**
     *
     * @return
     */
    public String getAuthor() {
        return author;
    }

    /**
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return
     */
    public String getUrlToImage() {
        return urlToImage;
    }

    /**
     *
     * @return
     */
    public String getPublishedAt() {
        return publishedAt;
    }

    /**
     *
     * @return
     */
    public String getUrl() {
        return url;
    }

    public String getContent() {
        return content;
    }

    public boolean isAddedToFav() {
        return isAddedToFav;
    }

    public void setAddedToFav(boolean addedToFav) {
        isAddedToFav = addedToFav;
    }
}
