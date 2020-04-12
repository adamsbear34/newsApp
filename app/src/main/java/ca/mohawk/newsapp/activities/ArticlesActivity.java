package ca.mohawk.newsapp.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ca.mohawk.newsapp.DB.DbHelper;
import ca.mohawk.newsapp.R;

public class ArticlesActivity extends AppCompatActivity {

    //VARIABLES
    private String description;
    private String title;
    private String source;
    private String time;
    private String  imgUrl;
    private String url;
    private String content;

    //WIDGETS
    TextView tvTitle;
    TextView tvSource;
    TextView tvDate;
    ImageView imageView;
    ImageButton btnFavourite;
    TextView tvContent;
    WebView webView;
    ProgressBar progressBar;
    Toolbar toolbar;


    //TOOLS


    //MANAGERS
    Intent intent;

    //DB
    DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        //INITIALIZING
        toolbar = findViewById(R.id.artcileToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*
        tvTitle = findViewById(R.id.MainTitle);
        tvDate = findViewById(R.id.MainDate);
        tvSource = findViewById(R.id.MainSource);
        tvContent = findViewById(R.id.mainContent);
        imageView= findViewById(R.id.mainImg);
        btnFavourite = findViewById(R.id.btnFav);


         */

        //VARIABLES
        /*
        title = intent.getStringExtra("TITLE");
        source = intent.getStringExtra("SOURCE");
        time = intent.getStringExtra("TIME");
        imgUrl = intent.getStringExtra("IMAGE_URL");
        description = intent.getStringExtra("DESCRIPTION");
        content = intent.getStringExtra("CONTENT");

         */

        intent = getIntent();

        url = intent.getStringExtra("URLTOSOURCE");
        Log.d("**MSG***", "url to source" + url);
        //SETTING CONTENT
        /*
        tvTitle.setText(title);
        tvDate.setText(time);
        tvSource.setText(source);
        tvContent.setText(content);
        Picasso.with(ArticlesActivity.this).load(imgUrl).into(imageView);

         */

        progressBar = findViewById(R.id.progressBar);


        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

        webView.setWebChromeClient( new WebChromeClient(){

            /**
             *
             * @param view
             * @param newProgress
             */
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100 && progressBar.getVisibility() == ProgressBar.GONE){
                    progressBar.setVisibility(ProgressBar.VISIBLE);

                }
                progressBar.setProgress(newProgress );
                if(newProgress == 100){
                    progressBar.setVisibility(ProgressBar.GONE);
                }
            }
        });


    }


    public void addToFavourite (){
        Log.d("**DB MSG***", " addToFavoirite is called");
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("title", title);
        values.put("source", source);
        values.put("description", description);

        long newrowID = db.insert("newsTable ", null, values);
        Log.d("**DB MSG***", "New ID " + newrowID);




    }
}
