package com.example.hp.smdproject.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;

import com.example.hp.smdproject.ActivityLayer.changeInfoUserActivity;
import com.example.hp.smdproject.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.hp.smdproject.R.id.checkBox;


/**
 * Created by Hussain on 25-Nov-17.
 */

public class Userinfo_adapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<String> mUserList;
    private List<String> filterList;
    CustomFilter filter;


    //Constructor

    public Userinfo_adapter(Context Context, List<String> mUserList) {
        this.mContext = Context;
        mInflater = LayoutInflater.from(mContext);
        this.mUserList = mUserList;
        this.filterList=mUserList;
    }

    @Override
    public int getCount() {
        return mUserList.size();
    }

    @Override
    public Object getItem(int position) {
        return mUserList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final ViewHolder holder;

        String s = mUserList.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.userinfolist, null);
            holder = new ViewHolder();
            holder.check = (CheckBox) convertView.findViewById(checkBox);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.check.setText(s);

        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String useremail;
                final boolean isChecked = holder.check.isChecked();
                // Do something here.
                if(isChecked)
                {
                    useremail= (String) holder.check.getText();
                    Log.d("isChecked","true1");
                    if(mContext instanceof changeInfoUserActivity){
                        Log.d("isChecked","true2");
                        ((changeInfoUserActivity)mContext).insertListData(useremail);
                    }
                }
                if(isChecked==false)
                {
                    useremail= (String) holder.check.getText();
                    Log.d("isChecked","false1");
                    if(mContext instanceof changeInfoUserActivity){
                        Log.d("isChecked","false2");
                        ((changeInfoUserActivity)mContext).deleteListData(useremail);
                    }

                }

            }
        });



        return convertView;

    }
    static class ViewHolder
    {
        CheckBox check;
    }
    public Filter getFilter() {
        // TODO Auto-generated method stub
        if(filter == null)
        {
            filter=new CustomFilter();
        }
        return filter;
    }
    //INNER CLASS
    class CustomFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // TODO Auto-generated method stub
            FilterResults results=new FilterResults();
            if(constraint != null && constraint.length()>0)
            {
                //CONSTARINT TO UPPER
                constraint=constraint.toString().toUpperCase();
                ArrayList<String> filters=new ArrayList<String>();
                //get specific items
                for(int i=0;i<filterList.size();i++)
                {
                    if(filterList.get(i).toUpperCase().contains(constraint))
                    {
                        String p=filterList.get(i);
                        filters.add(p);
                    }
                }
                results.count=filters.size();
                results.values=filters;
            }else
            {
                results.count=filterList.size();
                results.values=filterList;
            }
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // TODO Auto-generated method stub
            mUserList=(ArrayList<String>) results.values;
            notifyDataSetChanged();
        }
    }
}
