package com.labjob.e2all.Dashboard;

import android.app.ProgressDialog;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.labjob.e2all.R;
import java.util.List;


public class Dashboard_adapter extends ArrayAdapter<Dashboard_items> {

    //the hero list that will be displayed
    private List<Dashboard_items> getcontestlist;

    //the context object
    private Context mCtx;

    //here we are getting the herolist and context
    //so while creating the object of this adapter class we need to give herolist and context
    public Dashboard_adapter(List<Dashboard_items> getcontestlist, Context mCtx) {
        super(mCtx, R.layout.activity_dashboard, getcontestlist);
        this.getcontestlist = getcontestlist;
        this.mCtx = mCtx;
    }

    //this method will return the list item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //creating a view with our xml layout
        View listViewItem = inflater.inflate(R.layout.dashboard_list_items, null, true);

        //getting text views
        TextView c_name = listViewItem.findViewById(R.id.name);
        TextView startdate_ = listViewItem.findViewById(R.id.startdate);
        TextView enddate_ = listViewItem.findViewById(R.id.enddate);

        //Getting the hero for the specified position
        Dashboard_items d_items = getcontestlist.get(position);

        //setting hero values to textviews
        c_name.setText(d_items.getName());
        startdate_.setText((d_items.getStartdate()));
        enddate_.setText(d_items.getEnddate());

        //returning the listitem
        return listViewItem;
    }
}
