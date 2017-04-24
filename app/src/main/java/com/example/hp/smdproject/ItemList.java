package com.example.hp.smdproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ItemList extends AppCompatActivity {

    private List<Productdetailclass> mProductList;
    private ProductListAdapter adapter;
    private ListView lvProduct;

    DataBaseAdpter helper;
    List<String> list1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        helper=new DataBaseAdpter(this);
        list1 = new ArrayList<String>();
        mProductList = new ArrayList<>();

        lvProduct=(ListView)findViewById(R.id.products_listview);


//        mProductList.add(new Produtct_Item(1,"Clause pants",30,R.drawable.pant2));
//        mProductList.add(new Produtct_Item(1,"jeans",20,R.drawable.pants));
        CreateUI();



        adapter=new ProductListAdapter(getApplicationContext(),mProductList);
        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
               // long v=adapter.getItemId(position);
                long v=adapter.getlistid(position);

                Toast.makeText(getApplicationContext(),"Item is clicked no. "+v,Toast.LENGTH_SHORT).show();

                String sessionId= Long.toString(v);
                Intent intent = new Intent(getBaseContext(),Show_items.class);
                intent.putExtra("EXTRA_SESSION_ID", sessionId);
                startActivity(intent);

            }
        });


        lvProduct.setAdapter(adapter);
    }
    public void CreateUI()
    {
        int j=1;
        list1= helper.gettable5();
        for(int i=0;i<list1.size();i++) {
            if(j%5==0 && i!=0)
            {
                int result1=0;
                String des="";
                Log.d("get5",list1.get(i-4));

//                if(i<=4)
                    result1 = Integer.parseInt(list1.get(i-4));
//                if(i>4)
//                    des=list1.get(i-4);

                int result2 = Integer.parseInt(list1.get(i-3));
                Log.d("get4_1",list1.get(i-3));
                int P=Integer.parseInt(list1.get(i-2));
                Log.d("get3_1",list1.get(i-2));
                int S=Integer.parseInt(list1.get(i-1));
                Log.d("get2_1",list1.get(i-1));
                Log.d("get1_1",list1.get(i));

                mProductList.add(new Productdetailclass(result1,list1.get(i),S,P,result2,R.drawable.pant2));
            }
            j++;
        }
        return;
    }


}
