package com.example.hp.smdproject.adapter;

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

import com.example.hp.smdproject.ActivityLayer.ItemList;
import com.example.hp.smdproject.ActivityLayer.editProductDetailActivity;
import com.example.hp.smdproject.BuniessLayer.Productdetailclass;
import com.example.hp.smdproject.R;

import java.util.List;

public class ProductListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Productdetailclass> mProductList;
    private String name=null;

    //Constructor

    public ProductListAdapter(Context mContext, List<Productdetailclass> mProductList,String n) {
        this.mContext = mContext;
//        mProductList=new ArrayList<Country>();
        mInflater = LayoutInflater.from(mContext);
        this.mProductList = mProductList;
        name=n;
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

        ViewHolder holder=null;
        Productdetailclass p = mProductList.get(position);
        if (convertView == null) {
            if(mContext instanceof ItemList) {
                convertView = mInflater.inflate(R.layout.list_row, null);
                holder = new ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.Pname);
                holder.price = (TextView) convertView.findViewById(R.id.Pprice);
                holder.img = (ImageView) convertView.findViewById(R.id.Pimg);
                convertView.setTag(holder);
            }
            else if(mContext instanceof editProductDetailActivity) {
                convertView = mInflater.inflate(R.layout.editproduct_list, null);
                holder = new ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.editPname);
                holder.price = (TextView) convertView.findViewById(R.id.editPprice);
                holder.size = (TextView) convertView.findViewById(R.id.editPsize);
                holder.img = (ImageView) convertView.findViewById(R.id.editPimg);
                convertView.setTag(holder);
            }

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        int number=p.getPrice();
//        String numberAsString = Integer.toString(number);


        if(mContext instanceof editProductDetailActivity || mContext instanceof ItemList) {
            holder.name.setText(name);
//        holder.price.setText(numberAsString);
            holder.price.setText("$ " + p.getPrice());
//        holder.img.setImageResource(p.getImage());
            holder.img.setImageBitmap(p.getImage());
        }

        if(mContext instanceof editProductDetailActivity) {

            holder.size.setText(Integer.toString(p.getSize()));
        }


        return convertView;

    }
    static class ViewHolder
    {
        TextView name;
        TextView price,size;
        ImageView img;
    }
}

