package ca.mohawk.newsapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class for initializing retrofit library
 */
public class  RetrofitClient {

    private static final String BASE_URL = "https://newsapi.org/v2/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    /**
     *
     */
    public RetrofitClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     *
     * @return
     */
    public static synchronized RetrofitClient getInstance(){
        if(mInstance == null){
            mInstance = new RetrofitClient();
        }

        return mInstance;
    }

    /**
     *
     * @return
     */
    public newsapi getApi() {return retrofit.create(newsapi.class);}

}
