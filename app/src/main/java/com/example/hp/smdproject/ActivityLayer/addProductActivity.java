package com.example.hp.smdproject.ActivityLayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.hp.smdproject.R;

public class addProductActivity extends AppCompatActivity {


    EditText E1;
    String imageurl;
    boolean flag=true;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        E1= (EditText) findViewById(R.id.imageurl);
        spinner = (Spinner) findViewById(R.id.productspinner);
    }
    public void AddProduct(View v) {

        flag=true;

        if (v.getId() == R.id.addPD) {
            imageurl = E1.getText().toString();
            if (imageurl.isEmpty()) {
                E1.setError("Give Image Url");
                flag = false;
            }

        }
    }
}
