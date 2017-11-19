package com.example.hp.smdproject.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.smdproject.BuniessLayer.Productclass;
import com.example.hp.smdproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shoib on 4/14/2017.
 */

public class Categories_custom_adapter extends BaseAdapter {
    List<Productclass> result;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;
    private List<Productclass> filterList;
    CustomFilter filter;


    public Categories_custom_adapter(Context mContext,  List<Productclass> osNameList, int[] osImages) {
        // TODO Auto-generated constructor stub
        Log.d("Categories_","consturctor");
        result=osNameList;

        inflater = LayoutInflater.from(mContext);
        imageId=osImages;
        this.filterList=osNameList;
//        inflater = ( LayoutInflater )context.
//                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        Log.d("Categories_",Integer.toString(result.size()));
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    public long getlistid(int position)
    {
        Productclass p = result.get(position);
        return p.getID();

    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        Log.d("Categories_","Get View");

        Productclass p = result.get(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.categories_custom_adapter, null);
            holder.os_text =(TextView) convertView.findViewById(R.id.os_texts);
            holder.os_img =(ImageView) convertView.findViewById(R.id.os_images);
            convertView.setTag(holder);
           // Toast.makeText(context, "You Clicked ", Toast.LENGTH_SHORT).show();
            Log.d("Pname",p.getname());
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.os_text.setText(p.getname());

        holder.os_img.setImageBitmap(p.getImage());

//        rowView.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_SHORT).show();
//
//            }
//        });
        return convertView;
    }

    static class Holder
    {
        TextView os_text;
        ImageView os_img;
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
                ArrayList<Productclass> filters=new ArrayList<Productclass>();
                //get specific items
                for(int i=0;i<filterList.size();i++)
                {
                    if(filterList.get(i).getname().toUpperCase().contains(constraint))
                    {
                        Productclass p=new Productclass(filterList.get(i).getID(),filterList.get(i).getname(),filterList.get(i).getImage() );
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
            result=(ArrayList<Productclass>) results.values;
            notifyDataSetChanged();
        }
    }
}
