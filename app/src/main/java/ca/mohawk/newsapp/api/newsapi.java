package ca.mohawk.newsapp.api;


import ca.mohawk.newsapp.models.Headlines;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interface for creating POST and GET requests to News API
 */
public interface newsapi {



    //REQUESTS


    //GET REQ
    @GET("top-headlines")
    Call<Headlines> getHeadlines(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );

    //GET REQ
    @GET("everything")
    Call<Headlines> findNews(
            @Query("q") String query,
            @Query("apiKey") String apiKey
    );

}
