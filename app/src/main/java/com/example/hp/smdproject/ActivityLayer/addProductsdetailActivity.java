package com.example.hp.smdproject.ActivityLayer;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class addProductsdetailActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    JSONArray products = null;
    private static String url_get_user = "https://stopshop321.000webhostapp.com/add_new_productDetail.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_GAME_NAME = "product";
    private static final String TAG_GID = "ID";

    EditText E1,E2,E3,E4;
    String imageurl,productSize,productPrice,productDesc,Category_id,product_Category=null,ID=null;
    boolean flag=true,checkID=false;
    DataBaseAdpter data=new DataBaseAdpter(addProductsdetailActivity.this);
    List<String> name;
    Spinner spinner2;
    private String[] arraySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_productsdetail);
        Category_id = getIntent().getStringExtra("Category_ID1");



        E1= (EditText) findViewById(R.id.imageDetailurl);
        E2= (EditText) findViewById(R.id.productDetailsize);
        E3= (EditText) findViewById(R.id.productDetailPrice);
        E4= (EditText) findViewById(R.id.productDetaildesc);

        name=new ArrayList<>();


        name=data.getalltable2(Category_id);
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
        spinner2 = (Spinner) findViewById(R.id.productDetailspinner);
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

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                product_Category=parentView.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


    }

    public void AddProductDetail(View v) {
        flag=true;

        if (v.getId() == R.id.addPD) {
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


            if(product_Category==null)
            {
                product_Category=spinner2.getItemAtPosition(0).toString();

                product_Category=data.getallIDtable2(product_Category);
                Log.d("product_Category",product_Category);
            }
            else {
                product_Category=data.getallIDtable2(product_Category);
                Log.d("product_Category",product_Category);
            }



            if(flag)
            {
                new AddProductDetail().execute();
            }

        }
    }

    public void addproductDetailName(String id, String pid, String D, String S, String P, Bitmap B)
    {
        long id1=data.inserttable5(id,pid,S,D,P);
        String numberAsString = Long.toString(id1);
        Log.d("Data inserted",numberAsString);

        id1=data.inserttable5_2(id,B);

    }


    private class AddProductDetail extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(addProductsdetailActivity.this);
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
            Log.d("AddProductDetail", " IN");
            List<NameValuePair> params = new ArrayList<NameValuePair>();


            params.add(new BasicNameValuePair("product_category", product_Category));
            params.add(new BasicNameValuePair("image", imageurl));
            params.add(new BasicNameValuePair("size", productSize));
            params.add(new BasicNameValuePair("price", productPrice));
            params.add(new BasicNameValuePair("desc", productDesc));

            String email = null, error_text = null;
            JSONObject json = jsonParser.makeHttpRequest(url_get_user,
                    "GET", params);
            // check for success tag

            int success = 0;

            // Checking for SUCCESS TAG
            try {
                success = json.getInt(TAG_SUCCESS);
            } catch (Exception e) {
                Log.d("AddProductDetail", " not working");
            }
            Log.d("Success is:", Integer.toString(success));

            if (success == 1) {
                // successfully created product
                Log.d("Sucess", "ProductDetail Addd!");

                try {
                    products = json.getJSONArray(TAG_GAME_NAME);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // looping through All Patients
                for (int i = 0; i < products.length(); i++) {
                    JSONObject c = null;
                    try {
                        c = products.getJSONObject(i);
                        ID=c.getString(TAG_GID);
                        checkID=true;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            } else {

                // failed to create product
                Log.d("failed", "ProductDetail not added!");
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
            if(checkID==true)
            {
                String urldisplay = imageurl;
                Bitmap mIcon11 = null;
                try {
                    InputStream in = new java.net.URL(urldisplay).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);

                } catch (Exception e) {
//                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }

                addproductDetailName(ID,product_Category,productDesc,productSize,productPrice,mIcon11);
            }

            finish();
        }
    }
}
