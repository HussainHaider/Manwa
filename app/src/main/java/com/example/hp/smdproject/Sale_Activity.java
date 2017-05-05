package com.example.hp.smdproject;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Sale_Activity extends AppCompatActivity {

    private List<Saleproductclass> mProductList;
    private SaleProductadpter adapter;
    private ListView lvProduct;

    DataBaseAdpter helper;
    List<String> list1;
    String Products_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_);

        helper=new DataBaseAdpter(this);
        list1 = new ArrayList<String>();
        mProductList = new ArrayList<>();
        mProductList.clear();


        lvProduct=(ListView)findViewById(R.id.Salelistview);


        CreateUI();



        adapter=new SaleProductadpter(getApplicationContext(),mProductList);

        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // long v=adapter.getItemId(position);


                Toast.makeText(getApplicationContext(),"Item is clicked no. ",Toast.LENGTH_SHORT).show();


            }
        });


        lvProduct.setAdapter(adapter);
    }
    public void CreateUI()
    {
        int j=1;
        list1= helper.gettable10();
        Bitmap B=null;
        for(int i=0;i<list1.size();i++) {
            if(j%4==0 && i!=0)
            {
                int result1=0;
                String des="";

                int id_ = Integer.parseInt(list1.get(i-3));
                Log.d("get4_1",list1.get(i-3));
                if(helper.getImage2(list1.get(i-3))!=null)
                    B=helper.getImage2(list1.get(i-3));


                Log.d("get3_1",list1.get(i-2));
                String name=list1.get(i-2);
                Log.d("get2_1",list1.get(i-1));
                int price=Integer.parseInt(list1.get(i-1));
                Log.d("get1_1",list1.get(i));
                String description=list1.get(i);
                mProductList.add(new Saleproductclass(id_,name,price,description,B));

            }
            j++;
        }

        return;

    }


}
