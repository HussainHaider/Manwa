package com.example.hp.smdproject.ActivityLayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hp.smdproject.R;

public class AdminHomeActivity extends AppCompatActivity {

    Spinner spinner1,spinner2;
    Button Btn1,Btn2;
    TextView txt1,txt2;
    String categoryid=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);


        Btn1=(Button)findViewById(R.id.go_btn1);
        if (Btn1 != null) {
            Btn1.setVisibility(View.INVISIBLE);
        }
        Btn2=(Button)findViewById(R.id.go_btn2);
        if (Btn2 != null) {
            Btn2.setVisibility(View.INVISIBLE);
        }

        txt1=(TextView) findViewById(R.id.textcategory1);
        if (txt1 != null) {
            txt1.setVisibility(View.INVISIBLE);
        }
        txt2=(TextView) findViewById(R.id.textcategory2);
        if (txt2 != null) {
            txt2.setVisibility(View.INVISIBLE);
        }

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        if (spinner1 != null) {
            spinner1.setVisibility(View.INVISIBLE);
        }
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        if (spinner2 != null) {
            spinner2.setVisibility(View.INVISIBLE);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);
    }
    public void adminfuctionality(View v)
    {
        categoryid=null;

        if(v.getId()==R.id.deleteuser_btn)
        {
            Intent intent = new Intent(getApplicationContext(), changeInfoUserActivity.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.addproduct_btn)
        {
            Intent intent = new Intent(getApplicationContext(), addProductActivity.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.addproductdetail_btn)
        {
            Btn1.setVisibility(View.VISIBLE);
            txt1.setVisibility(View.VISIBLE);
            spinner1.setVisibility(View.VISIBLE);
        }
        else if(v.getId()==R.id.editproduct_btn)
        {
            Btn2.setVisibility(View.VISIBLE);
            txt2.setVisibility(View.VISIBLE);
            spinner2.setVisibility(View.VISIBLE);

        }
        else if(v.getId()==R.id.go_btn1)
        {
            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // your code here
                    categoryid=parentView.getItemAtPosition(position).toString();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });

            if(categoryid==null)
            {
                categoryid=spinner1.getItemAtPosition(0).toString();
            }


            if(categoryid.equals("Male"))
            {
                categoryid="1";
            }
            else if(categoryid.equals("Female"))
            {
                categoryid="2";
            }


            Intent intent = new Intent(getApplicationContext(), addProductsdetailActivity.class);
            intent.putExtra("Category_ID1", categoryid);
            startActivity(intent);
        }
        else if(v.getId()==R.id.go_btn2)
        {
            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // your code here
                    categoryid=parentView.getItemAtPosition(position).toString();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });

            if(categoryid==null)
            {
                categoryid=spinner2.getItemAtPosition(0).toString();
            }


            if(categoryid.equals("Male"))
            {
                categoryid="1";
            }
            else if(categoryid.equals("Female"))
            {
                categoryid="2";
            }


            Intent intent = new Intent(getApplicationContext(), editProductActivity.class);
            intent.putExtra("Category_ID2", categoryid);
            startActivity(intent);
        }
    }
}
