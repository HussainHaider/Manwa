package com.example.hp.smdproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by shoib on 4/14/2017.
 */

public class Categories_custom_adapter extends BaseAdapter {
    List<Productclass> result;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;
    public Categories_custom_adapter(Categories mainActivity,  List<Productclass> osNameList, int[] osImages) {
        // TODO Auto-generated constructor stub
        result=osNameList;
        context=mainActivity;
        imageId=osImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
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


    public class Holder
    {
        TextView os_text;
        ImageView os_img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;

        Productclass p = result.get(position);

        rowView = inflater.inflate(R.layout.categories_custom_adapter, null);
        holder.os_text =(TextView) rowView.findViewById(R.id.os_texts);
        holder.os_img =(ImageView) rowView.findViewById(R.id.os_images);

        Log.d("Pname",p.getname());
        holder.os_text.setText(p.getname());

        holder.os_img.setImageResource(imageId[position]);

//        rowView.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_SHORT).show();
//
//            }
//        });
        return rowView;
    }
}
