package com.example.hp.smdproject.ActivityLayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hp.smdproject.R;

public class AdminHomeActivity extends AppCompatActivity {

    Spinner spinner1;
    Button Btn;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);


        Btn=(Button)findViewById(R.id.go_btn);
        if (Btn != null) {
            Btn.setVisibility(View.INVISIBLE);
        }
        txt=(TextView) findViewById(R.id.textcategory);
        if (txt != null) {
            txt.setVisibility(View.INVISIBLE);
        }
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        if (spinner1 != null) {
            spinner1.setVisibility(View.INVISIBLE);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
    }
    public void adminfuctionality(View v)
    {
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
            Btn.setVisibility(View.VISIBLE);
            txt.setVisibility(View.VISIBLE);
            spinner1.setVisibility(View.VISIBLE);
        }
        else if(v.getId()==R.id.editproduct_btn)
        {

        }
        else if(v.getId()==R.id.go_btn)
        {

            Intent intent = new Intent(getApplicationContext(), addProductsdetailActivity.class);
            startActivity(intent);
        }
    }
}
