package com.example.hp.smdproject;

/**
 * Created by HP on 16-Feb-17.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ProductListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Productdetailclass> mProductList;

    //Constructor

    public ProductListAdapter(Context mContext, List<Productdetailclass> mProductList) {
        this.mContext = mContext;
//        mProductList=new ArrayList<Country>();
        mInflater = LayoutInflater.from(mContext);
        this.mProductList = mProductList;
    }

    @Override
    public int getCount() {
        return mProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public long getlistid(int position)
    {
        Productdetailclass p = mProductList.get(position);
        return p.getID();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        ViewHolder holder;
        Productdetailclass p = mProductList.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_row, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.Pname);
            holder.price= (TextView) convertView.findViewById(R.id.Pprice);
            holder.img= (ImageView) convertView.findViewById(R.id.Pimg);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        int number=p.getPrice();
//        String numberAsString = Integer.toString(number);


        holder.name.setText("Jeans");
//        holder.price.setText(numberAsString);
        holder.price.setText("$ "+p.getPrice());
//        holder.img.setImageResource(p.getImage());
        holder.img.setImageBitmap(p.getImage());


        return convertView;

    }
    static class ViewHolder
    {
        TextView name;
        TextView price;
        ImageView img;
    }
}

