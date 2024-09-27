package com.example.app2u_provatecnica_luismedina;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomAdapter extends BaseAdapter {

    private final Context context;
    private final ArrayList<HashMap<String, String>> itemList;

    public CustomAdapter(Context context, ArrayList<HashMap<String, String>> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HashMap<String, String> item = itemList.get(position);

        if (item.get("is_removed").equals("false")) {
            View view = LayoutInflater.from(context).inflate(R.layout.list_row, null);
            ImageView imageView = view.findViewById(R.id.image);
            TextView name = view.findViewById(R.id.name);
            TextView id = view.findViewById(R.id.iditem);

            // Bind data to the views
            String imageUrl = item.get("image");
            String bname = item.get("first_name") + " " + item.get("last_name");
            String bid = item.get("id");

            // Load image
            Picasso.get().load(imageUrl).placeholder(R.drawable.artist_placeholder).into(imageView);

            // Set the text
            name.setText(bname);
            id.setText(bid);

            return view;
        } else {
            // Return a dummy view with zero height
            View dummyView = new View(context);
            dummyView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
            return dummyView;
        }
    }
}
