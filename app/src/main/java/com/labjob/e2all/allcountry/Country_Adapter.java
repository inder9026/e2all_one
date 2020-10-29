package com.labjob.e2all.allcountry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.labjob.e2all.R;

import java.util.List;

public class Country_Adapter extends ArrayAdapter<Country> {

    //the hero list that will be displayed
    private List<Country> Country_List;

    //the context object
    private Context mCtx;

    //here we are getting the herolist and context
    //so while creating the object of this adapter class we need to give herolist and context
    public Country_Adapter(List<Country> Country_List, Context mCtx) {
        super(mCtx, R.layout.activity_all_countires, Country_List);
        this.Country_List = Country_List;
        this.mCtx = mCtx;
    }

    //this method will return the list item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //creating a view with our xml layout
        View listViewItem = inflater.inflate(R.layout.all_country_list_item, null, true);

        //getting text views
        TextView c_name = listViewItem.findViewById(R.id.C_name);
        TextView c_sort_name = listViewItem.findViewById(R.id.C_sort_name);
        TextView c_phone_code = listViewItem.findViewById(R.id.phone_code);

        //Getting the hero for the specified position
        Country country = Country_List.get(position);

        //setting hero values to textviews
        c_name.setText(country.getName());
        c_sort_name.setText((country.getSortname()));
        c_phone_code.setText(country.getPhonecode());

        //returning the listitem
        return listViewItem;
    }
}