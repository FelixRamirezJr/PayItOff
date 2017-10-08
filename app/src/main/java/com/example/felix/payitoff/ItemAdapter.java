package com.example.felix.payitoff;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by felix on 10/7/17.
 */

public class ItemAdapter extends BaseAdapter {
    List<Item> myList;
    View feedItemLayout;
    Application applicationContext;
    Activity activity;
    private LayoutInflater inflater;
    Item item;


    public ItemAdapter(Activity activity, List<Item> items)
    {
        this.activity = activity;
        this.myList = items;
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Object getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_view, null);
        }

        item = myList.get(position);

        TextView name = (TextView) convertView.findViewById(R.id.item_name);

        name.setText( item.name );


        return convertView;
    }
}