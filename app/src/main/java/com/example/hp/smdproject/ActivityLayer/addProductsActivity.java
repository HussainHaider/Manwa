package com.example.hp.smdproject.ActivityLayer;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.hp.smdproject.DataLayer.DataBaseAdpter;
import com.example.hp.smdproject.JSONParser;
import com.example.hp.smdproject.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class addProductsActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    JSONArray Users = null;
    private static String url_get_user = "https://stopshop321.000webhostapp.com/add_new_product.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    EditText E1,E2,E3,E4;
    String imageurl,productSize,productPrice,productDesc;
    boolean flag=true;
    DataBaseAdpter data=new DataBaseAdpter(addProductsActivity.this);
    List<String> name;
    Spinner spinner1,spinner2;
    private String[] arraySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);

        E1= (EditText) findViewById(R.id.imageurl);
        E2= (EditText) findViewById(R.id.productsize);
        E3= (EditText) findViewById(R.id.productPrice);
        E4= (EditText) findViewById(R.id.productdesc);

        name=new ArrayList<>();


        spinner1 = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);

        name=data.getalltable2();
        Log.d("check_add","dataget");
        arraySpinner = new String[name.size()];
        Log.d("check_add","dataget1");
        int i=0;
        while (i<name.size()) {
            Log.d("check_add","dataget2");
                Log.d("check_add","dataget3");
                arraySpinner[i] = name.get(i);
            i+=1;
        }

        Log.d("check_add","dataget5");
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        Log.d("check_add","dataget6");
        ArrayAdapter<String> adap = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        Log.d("check_add","dataget7");
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Log.d("check_add","dataget8");

        if (spinner2 != null) {
            Log.d("check_add","dataget9");
            spinner2.setAdapter(adap);
            Log.d("check_add","dataget10");
        }
        Log.d("check_add","dataget11");
    }

    public void AddProduct(View v) {
        flag=true;

        if (v.getId() == R.id.addP) {
            imageurl=E1.getText().toString();
            if(imageurl.isEmpty())
            {
                E1.setError("Give Image Url");
                flag=false;
            }
            productSize=E2.getText().toString();
            if(productSize.isEmpty())
            {
                E1.setError("Give Size");
                flag=false;
            }
            productPrice=E3.getText().toString();
            if(productPrice.isEmpty())
            {
                E1.setError("Give Price");
                flag=false;
            }
            productDesc=E4.getText().toString();
            if(productDesc.isEmpty())
            {
                E1.setError("Give Descriptive");
                flag=false;
            }
            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // your code here
                    parentView.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });


            if(flag==true)
            {
                new AddProduct().execute();
            }

        }
    }


    class AddProduct extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(addProductsActivity.this);
            pDialog.setMessage("Loading data. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {
            // Building Parameters
            Log.d("AddProduct", " IN");
            List<NameValuePair> params = new ArrayList<NameValuePair>();


            params.add(new BasicNameValuePair("product_category", Integer.toString(1)));
            params.add(new BasicNameValuePair("image", imageurl));
            params.add(new BasicNameValuePair("size", productSize));
            params.add(new BasicNameValuePair("price", productPrice));
            params.add(new BasicNameValuePair("desc", productDesc));

            String email = null, error_text = null;
            JSONObject json = jsonParser.makeHttpRequest(url_get_user,
                    "POST", params);
            // check for success tag

            int success = 0;

            // Checking for SUCCESS TAG
            try {
                success = json.getInt(TAG_SUCCESS);
            } catch (Exception e) {
                Log.d("AddProduct", " not working");
            }
            Log.d("Success is:", Integer.toString(success));

            if (success == 1) {
                // successfully created product
                Log.d("Sucess", "Product Addd!");

            } else {

                // failed to create product
                Log.d("failed", "Product not added!");
                try {
                    error_text = (String) json.get(TAG_MESSAGE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("LOss", error_text);
            }


            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once don
            pDialog.dismiss();
        }
    }
}
