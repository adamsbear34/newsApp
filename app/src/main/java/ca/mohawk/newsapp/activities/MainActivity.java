package ca.mohawk.newsapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ca.mohawk.newsapp.Adapters.RecyclerViewAdapter;
import ca.mohawk.newsapp.R;
import ca.mohawk.newsapp.api.RetrofitClient;
import ca.mohawk.newsapp.models.Articles;
import ca.mohawk.newsapp.models.Headlines;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    //WIDGETS
    RecyclerView recyclerView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    EditText edSearch;
    Button btnSeatch;

    //VARIABLES
    List<Articles> articlesList;
    String country;
    String API_KEY;



   //ADAPTERS
    RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //INITIALIZATION
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        btnSeatch = findViewById(R.id.btnSearch);
        edSearch = findViewById(R.id.edSearch);
        articlesList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        country = "us";
        API_KEY = getResources().getString(R.string.API_KEY);


        //LISTENERS
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        //LISTENERS
        navigationView.setNavigationItemSelectedListener(this);

        getHeadLines(API_KEY, country);

        btnSeatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = edSearch.getText().toString();
                getSearchedArticle(query, API_KEY);
                edSearch.setText(" ");

            }
        });

    }

    /**
     *
     * @param apiKey
     * @param country
     */
    public void getHeadLines(String apiKey, String country){
        Log.d("**MSG", "GET HEADLINES IS CALLED");

        Call<Headlines> call = RetrofitClient
                .getInstance()
                .getApi()
                .getHeadlines(country, apiKey);

        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                Log.d("**MSG", "GOT A RESPONSE");
                try {

                    if(response.isSuccessful() && response.body().getArticles() != null){
                        Log.d("**MSG", "RESPONSE IS SUCCESSFUL");
                        articlesList.clear();
                        articlesList = response.body().getArticles();
                        recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, articlesList);
                        recyclerView.setAdapter(recyclerViewAdapter);
                    }else{
                        String s = response.errorBody().string();
                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                        Log.d("SEVER Response", s);
                    }

                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                Log.d("**MSG", "RESPONSE FAILD" + t.getMessage());
            }
        });

    }


    /**
     *
     * @param query
     * @param apiKey
     */
    public void getSearchedArticle(String query, String apiKey){

        Call<Headlines> call = RetrofitClient
                .getInstance()
                .getApi()
                .findNews(query, apiKey);

        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                Log.d("**MSG", "GOT A RESPONSE");
                try {

                    if(response.isSuccessful() && response.body().getArticles() != null){
                        Log.d("**MSG", "RESPONSE IS SUCCESSFUL");
                        articlesList.clear();
                        articlesList = response.body().getArticles();
                        recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, articlesList);
                        recyclerView.setAdapter(recyclerViewAdapter);
                    }else{
                        String s = response.errorBody().string();
                        Toast.makeText(MainActivity.this, "Nothing was found", Toast.LENGTH_LONG).show();
                        Log.d("SEVER Response", s);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                Log.d("**MSG", "RESPONSE FAILD" + t.getMessage());
            }
        });


    }


    /**
     *
     */
    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id){

            case R.id.nav_favorite:
                startActivity(new Intent(this, FavouriteActivity.class));
                break;

            case R.id.nav_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
        }

        return false;
    }
}
