package com.example.hp.smdproject.ActivityLayer;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

import static android.os.Build.ID;

public class editProductInfoActivity extends AppCompatActivity {

    EditText E1,E2,E3,E4;
    String Products_id=null;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    JSONArray products = null;
    private static String url_get_user = "https://stopshop321.000webhostapp.com/getProductdetailbyID.php";
    private static String url_update_product = "https://stopshop321.000webhostapp.com/update_productDetail.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_Price = "Price";
    private static final String TAG_Size = "size";
    private static final String TAG_Desc = "Description";
    private static final String TAG_Image = "Image";
    private static final String TAG_GAME_NAME = "productdetails";

    String Price=null,Size=null,Desc=null,Image=null;
    DataBaseAdpter data=new DataBaseAdpter(editProductInfoActivity.this);
    boolean checkID=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editproductinfo);

        E1= (EditText) findViewById(R.id.priceinfo);
        E2= (EditText) findViewById(R.id.sizeinfo);
        E3= (EditText) findViewById(R.id.Descinfo);
        E4= (EditText) findViewById(R.id.imageinfo);

        Products_id = getIntent().getStringExtra("EXTRA_SESSION_ID");
        new GetProductDetail().execute();


    }
    public  void UpgradeProduct(View v)
    {
        if(v.getId()==R.id.update_btn)
        {
            Price=E1.getText().toString();
            Size=E2.getText().toString();
            Desc=E3.getText().toString();
            Image=E4.getText().toString();

            new UpdateProductDetail().execute();
        }
    }
    public void addproductDetailName(String id, String D, String S, String P, Bitmap B)
    {
        long id1=data.updatetable5(id,S,D,P);
        String numberAsString = Long.toString(id1);
        Log.d("Data inserted",numberAsString);

        id1=data.inserttable5_2(id,B);

    }


    private class GetProductDetail extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(editProductInfoActivity.this);
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

            params.add(new BasicNameValuePair("ID", Products_id));

            String  error_text = null;
            JSONObject json = jsonParser.makeHttpRequest(url_get_user,
                    "GET", params);
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
                        Price=c.getString(TAG_Price);
                        Desc=c.getString(TAG_Desc);
                        Size=c.getString(TAG_Size);
                        Image=c.getString(TAG_Image);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


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
            E1.setText(Price, TextView.BufferType.EDITABLE);
            E2.setText(Size, TextView.BufferType.EDITABLE);
            E3.setText(Desc, TextView.BufferType.EDITABLE);
            E4.setText(Image, TextView.BufferType.EDITABLE);

        }
    }

    private class UpdateProductDetail extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(editProductInfoActivity.this);
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
            Log.d("UpdateProductDetail", " IN");
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("image", Image));
            params.add(new BasicNameValuePair("size", Size));
            params.add(new BasicNameValuePair("price", Price));
            params.add(new BasicNameValuePair("desc", Desc));
            params.add(new BasicNameValuePair("ID", Products_id));

            String  error_text = null;
            JSONObject json = jsonParser.makeHttpRequest(url_update_product,
                    "POST", params);
            // check for success tag

            int success = 0;

            // Checking for SUCCESS TAG
            try {
                success = json.getInt(TAG_SUCCESS);
            } catch (Exception e) {
                Log.d("UpdateProductDetail", " not working");
            }
            Log.d("Success is:", Integer.toString(success));

            if (success == 1) {
                // successfully created product
                Log.d("Sucess", "UpdateProductDetail!");
                checkID=true;


            } else {

                // failed to create product
                Log.d("failed", "UpdateProductDetail!");
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
                String urldisplay = Image;
                Bitmap mIcon11 = null;
                try {
                    InputStream in = new java.net.URL(urldisplay).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);

                } catch (Exception e) {
//                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }

                addproductDetailName(Products_id,Desc,Size,Price,mIcon11);
            }

            finish();
        }
    }
}
