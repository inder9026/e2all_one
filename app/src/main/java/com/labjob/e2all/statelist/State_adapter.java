package com.labjob.e2all.statelist;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.labjob.e2all.R;
import com.labjob.e2all.allcountry.Country;

import java.util.List;

public class State_adapter extends ArrayAdapter<State> {

    //the hero list that will be displayed
    private List<State> state_List;

    //the context object
    private Context mCtx;

    //here we are getting the herolist and context
    //so while creating the object of this adapter class we need to give herolist and context
    public State_adapter(List<State> state_List, Context mCtx) {
        super(mCtx, R.layout.activity_state_list, state_List);
        this.state_List = state_List;
        this.mCtx = mCtx;
    }

    //this method will return the list item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //creating a view with our xml layout
        View listViewItem = inflater.inflate(R.layout.state_list_item, null, true);

        //getting text views
        TextView c_name = listViewItem.findViewById(R.id.C_name);
        TextView c_sort_name = listViewItem.findViewById(R.id.C_sort_name);
        TextView c_phone_code = listViewItem.findViewById(R.id.phone_code);

        //Getting the hero for the specified position
        State state = state_List.get(position);

        //setting hero values to textviews
        c_name.setText(state.getName());
        c_sort_name.setText((state.getcountry_id()));
        c_phone_code.setText(state.getid());

        //returning the listitem
        return listViewItem;
    }
}
