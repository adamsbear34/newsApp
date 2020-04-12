package ca.mohawk.newsapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.LocaleDisplayNames;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ca.mohawk.newsapp.Adapters.ListViewAdapter;
import ca.mohawk.newsapp.DB.DbHelper;
import ca.mohawk.newsapp.R;
import ca.mohawk.newsapp.models.Articles;


/**
 *
 */
public class FavouriteActivity extends AppCompatActivity {

    //VARIABLES
    ArrayList<String> titles;
    ArrayList<String> sources;
    ArrayList<String> descriptions;
    String title;

    String[] titlesArr;
    String[] sourcesArr;
    String[] descriptionsArr;
    private int mPosition;

    Toolbar toolbar;

    //WIDGETS
    ListView listView;


    //DB
    DbHelper dbHelper;

    //Adapter
    ListViewAdapter listViewAdapter;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        //Initialization
        listView = findViewById(R.id.favouriteList);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Calling get Dat
        getData();



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(FavouriteActivity.this, titlesArr[i], Toast.LENGTH_SHORT).show();
                mPosition = i;
                title = titlesArr[i];
                showDialog(1);
            }
        });
    }


    /**
     *
     */
    public void getData(){

        dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projections = {"title, source, description"};
        Cursor cursor = db.query("newsTable", projections, null, null, null, null, null);


        titles = new ArrayList<>();
        sources = new ArrayList<>();
        descriptions = new ArrayList<>();



        while (cursor.moveToNext()){

            String title = cursor.getString(cursor.getColumnIndex("title"));
            String source = cursor.getString(cursor.getColumnIndex("source"));
            String description = cursor.getString(cursor.getColumnIndex("description"));
            titles.add(title);
            sources.add(source);
            descriptions.add(description);


        }
        cursor.close();

        //Show results

        titlesArr = titles.toArray(new String[titles.size()]);
        sourcesArr = sources.toArray(new String[sources.size()]);
        descriptionsArr = descriptions.toArray(new String[descriptions.size()]);


        if(titlesArr.length == 0){

            Toast.makeText(this,"You have no saved articles", Toast.LENGTH_LONG).show();

        }else{

            listViewAdapter = new ListViewAdapter(FavouriteActivity.this,titlesArr, sourcesArr,descriptionsArr);
            listView.setAdapter(listViewAdapter);
        }

    }


    /**
     *
     * @param id
     * @return
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id){

            case 1:
                String[] selectItems = {"Read", "Remove", "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Chose on of the following");
                builder.setItems(selectItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        switch (i){
                            case 0:
                                goToSource();
                                break;

                            case 1:
                                deleteFromFav(title);
                                break;

                            case 2:
                                dialogInterface.cancel();
                                break;
                        }
                    }
                });
                return builder.create();

                default:
                    return null;
        }

    }

    /**
     *
     */
    public void  goToSource(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(sourcesArr[mPosition]));
        startActivity(intent);

    }

    /**
     *
     * @param titleName
     */
    public void deleteFromFav(String titleName){
        dbHelper.deleteRecord(titleName);
        startActivity( new Intent(this, MainActivity.class)) ;
    }
}
