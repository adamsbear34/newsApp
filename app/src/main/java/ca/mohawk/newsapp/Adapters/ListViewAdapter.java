package ca.mohawk.newsapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ListAdapter;

import java.util.concurrent.CopyOnWriteArrayList;

import ca.mohawk.newsapp.R;
import ca.mohawk.newsapp.models.Articles;

public class ListViewAdapter extends ArrayAdapter<String> {

    Context context;
    String[] titles;
    String[] sources;
    String[] descriptions;


    /**
     *
     * @param context
     * @param titles
     * @param sources
     * @param descriptions
     */

    public ListViewAdapter(Context context, String[] titles, String[] sources, String[] descriptions){
            super(context, R.layout.favourite_header, R.id.favTitle, titles);

            this.context = context;
            this.titles = titles;
            this.sources = sources;
            this.descriptions = descriptions;

    }


    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.favourite_header, parent, false);

        TextView title = row.findViewById(R.id.favTitle);
        TextView source = row.findViewById(R.id.favSource);
        TextView description = row.findViewById(R.id.favDesciption);


        title.setText(titles[position]);
        source.setText(sources[position]);
        description.setText(descriptions[position]);

        return row;

    }
}
