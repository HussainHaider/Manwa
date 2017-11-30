package com.example.hp.smdproject.ActivityLayer;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.hp.smdproject.DataLayer.DataBaseAdpter;
import com.example.hp.smdproject.R;
import com.example.hp.smdproject.adapter.Userinfo_adapter;

import java.util.ArrayList;
import java.util.List;

public class editProductActivity extends AppCompatActivity {

    String Category_id=null;
    private Userinfo_adapter adapter;
    private ListView lvProduct;
    DataBaseAdpter data=null;
    private List<String> ListofProduct;
    private List<String> productlist;

    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        Category_id = getIntent().getStringExtra("Category_ID2");
        ListofProduct = new ArrayList<>();

        productlist = new ArrayList<>();
        lvProduct=(ListView)findViewById(R.id.Productlist);
        data=new DataBaseAdpter(editProductActivity.this);
        Log.d("Category_id",Category_id);
        productlist=data.getalltable2(Category_id);
        txt=(TextView)findViewById(R.id.error_txt);
        txt.setVisibility(View.INVISIBLE);

        adapter=new Userinfo_adapter(editProductActivity.this,productlist);
        lvProduct.setAdapter(adapter);

    }
    public void ProductUpgradition(View v)
    {
        if(v.getId()==R.id.btndeleteProduct)
        {


        }
        if(v.getId()==R.id.btnsEditProduct)
        {
            if(ListofProduct.size()==1)
            {
                
            }
            else
            {
                txt.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        boolean Flag=true;
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                Log.i("well", " this worked");
                return false;
            }
        });
        return Flag;
    }
    public void insertListData(String text)
    {
        Log.d("Data","added");
        ListofProduct.add(text);
    }
    public void deleteListData(String text)
    {
        Log.d("Data","remove");
        ListofProduct.remove(text);
    }


}
