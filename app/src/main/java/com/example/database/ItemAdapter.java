package com.example.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<String> {

    Context context;
    ArrayList<String> rTitle;
    ArrayList<String> rDescription;

    ItemAdapter(Context context, ArrayList<String> title, ArrayList<String> description) {
        super(context, R.layout.item, R.id.description_item, description);
        this.context = context;
        this.rTitle = title;
        this.rDescription = description;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item = layoutInflater.inflate(R.layout.item, parent, false);
        TextView title_item = item.findViewById(R.id.title_item);
        TextView description_item = item.findViewById(R.id.description_item);

        title_item.setText(rTitle.get(position));
        description_item.setText(rDescription.get(position));

        return item;
    }
}
