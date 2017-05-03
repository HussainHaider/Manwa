package com.example.hp.smdproject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by shoib on 4/15/2017.
 */

public class cart_custom_adapter extends ArrayAdapter<Productdetailclass> {

    private final Activity context;
    ArrayList<Productdetailclass> p;
    private LayoutInflater inflater;
    DataBaseAdpter Helper;


    public cart_custom_adapter(Activity context,
                               ArrayList<Productdetailclass> list) {
        super(context, 0,list);
        this.context = context;
        this.p = list;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        Helper = new DataBaseAdpter(context);



    }
    public ArrayList<Productdetailclass> getFilteredNotes() {
        return p;
    }
    public Productdetailclass getItem(int position)
    {
        return p.get(position);
    }
    public int getCount()
    {
        return p.size();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {


        if(view == null)
        {
            view = inflater.inflate(R.layout.cart_custom_listview, null);
        }


        TextView txtTitle = (TextView) view.findViewById(R.id.txt);
        txtTitle.setText(Integer.toString( p.get(position).PID));

        TextView txtTitle1 = (TextView) view.findViewById(R.id.size);
         txtTitle1.setText(Integer.toString( p.get(position).Size));
        TextView txtTitle2 = (TextView) view.findViewById(R.id.price);
        txtTitle2.setText(Integer.toString( p.get(position).Price));

        ImageView imageView = (ImageView) view.findViewById(R.id.img);
        imageView.setImageBitmap( p.get(position).image);

        Button button = (Button) view.findViewById(R.id.button_remove);
        button.setTag(position);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer index = (Integer) v.getTag();
                long c= Helper.removeFromCart(Integer.toString( p.get(index).PID));
                if(c==-1) {
                    Toast.makeText(context, "Item not removed from cart", Toast.LENGTH_SHORT)
                            .show();
                }
                else
                {
                    Toast.makeText(context, "Item Removed from Cart Successfully", Toast.LENGTH_SHORT)
                            .show();

                }


                p.remove(index.intValue());
                notifyDataSetChanged();

            }
        });

        return view;
    }
}
