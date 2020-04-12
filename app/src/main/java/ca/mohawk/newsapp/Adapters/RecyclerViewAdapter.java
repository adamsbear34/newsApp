package ca.mohawk.newsapp.Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ca.mohawk.newsapp.DB.DbHelper;
import ca.mohawk.newsapp.R;
import ca.mohawk.newsapp.activities.ArticlesActivity;
import ca.mohawk.newsapp.activities.MainActivity;
import ca.mohawk.newsapp.models.Articles;

/**
 *
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    //DECLARATION
    Context context;
    List<Articles> articles;

    //DB
    DbHelper dbHelper;

    /**
     *
     * @param context
     * @param articles
     */
    public RecyclerViewAdapter(Context context, List<Articles> articles) {
        this.context = context;
        this.articles = articles;
        dbHelper = new DbHelper(context);
    }

    /**
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.headline,parent, false);
        return new ViewHolder(view);
    }

    /**
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Articles a = articles.get(position);
        holder.tvTitle.setText(a.getTitle());
        holder.tvSource.setText(a.getSource().getName());
        holder.tvDate.setText(dateRefactor(a.getPublishedAt()));
        a.setAddedToFav(false);


        String URL = a.getUrlToImage();
        String URLTOSOURCE = a.getUrl();
        String content = a.getContent();

        Picasso.with(context).load(URL).into(holder.imageView);

        /**
         *
         */
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ArticlesActivity.class);
                intent.putExtra("URLTOSOURCE", a.getUrl());
                /*
                intent.putExtra("TITLE", a.getTitle());
                intent.putExtra("SOURCE", a.getSource().getName());
                intent.putExtra("TIME", dateRefactor(a.getPublishedAt()));
                intent.putExtra("IMAGE_URL", a.getUrlToImage());
                intent.putExtra("URLTOSOURCE", a.getUrl());
                intent.putExtra("DESCRIPTION", a.getDescription());
                intent.putExtra("CONTENT", a.getContent());
                 */

                context.startActivity(intent);
            }
        });

        /**
         *
         */
        holder.btnToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("**DB MSG**", "addToFAv is Called");
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                if (!a.isAddedToFav()){
                    values.put("title", a.getTitle() );
                    values.put("source", a.getUrl());
                    values.put("description", a.getDescription());
                    long newrowID = db.insert("newsTable ", null, values);
                    a.setAddedToFav(true);
                    Toast.makeText(view.getContext(), "Added to Favorite" + newrowID, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(view.getContext(), "This article is already added", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    /**
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return articles.size();
    }

    /**
     *
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvSource;
        TextView tvDate;
        ImageView imageView;
        CardView cardView;
        ImageView btnToFav;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.headTitle);
            tvSource = itemView.findViewById(R.id.headSource);
            tvDate = itemView.findViewById(R.id.headDate);
            imageView = itemView.findViewById(R.id.headImg);
            cardView = itemView.findViewById(R.id.cardView);
            btnToFav = itemView.findViewById(R.id.btnAddToFav);
        }
    }


    /**
     *
     * @param time
     */
    public String dateRefactor(String time){
        PrettyTime prettyTime = new PrettyTime(new Locale(getTime()));
        String localTime = null;

        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:", Locale.ENGLISH);
            Date date = simpleDateFormat.parse(time);
            localTime = prettyTime.format(date);
        }catch (ParseException e){

            e.printStackTrace();
        }
        return localTime;
    }

    /**
     *
     * @return
     */
    public String getTime(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }


}
