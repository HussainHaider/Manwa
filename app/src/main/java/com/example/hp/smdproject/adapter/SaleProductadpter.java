package com.example.hp.smdproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.smdproject.BuniessLayer.Saleproductclass;
import com.example.hp.smdproject.R;

import java.util.List;

/**
 * Created by HP on 03-May-17.
 */
public class SaleProductadpter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Saleproductclass> mProductList;

    //Constructor

    public SaleProductadpter(Context mContext, List<Saleproductclass> mProductList) {
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
//    public long getlistid(int position)
//    {
//        Productdetailclass p = mProductList.get(position);
//        return p.getID();
//
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        ViewHolder holder;
        Saleproductclass p = mProductList.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.sale_listrow, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.SPname);
            holder.price= (TextView) convertView.findViewById(R.id.SPprice);
            holder.img= (ImageView) convertView.findViewById(R.id.SPimg);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        int number=p.getPrice();
//        String numberAsString = Integer.toString(number);


        holder.name.setText(p.getName());
//        holder.price.setText(numberAsString);
        holder.price.setText("$ "+p.getPrice());
//        holder.img.setImageResource(R.drawable.pant2);
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
