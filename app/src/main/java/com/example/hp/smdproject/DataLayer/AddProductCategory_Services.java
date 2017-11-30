package com.example.hp.smdproject.DataLayer;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import com.example.hp.smdproject.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hussain on 30-Nov-17.
 */

public class AddProductCategory_Services extends Service {
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    JSONArray Users = null;
    private static String url_get_user = "https://stopshop321.000webhostapp.com/add_new_product3.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_GAME_NAME = "product";

    String ProductID=null,categoryID=null;

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent pIntent, int flags, int startId) {
        // TODO Auto-generated method stub
        ProductID = pIntent.getStringExtra("ProductID");
        categoryID = pIntent.getStringExtra("categoryID");


        callaysnc();
        return super.onStartCommand(pIntent, flags, startId);
    }
    public void callaysnc()
    {
        Log.d("Category","Service");
        new AddProductCategory().execute();

    }
    private class AddProductCategory extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {
            // Building Parameters
            Log.d("AddProduct_Service", " IN");
            List<NameValuePair> params = new ArrayList<NameValuePair>();


            params.add(new BasicNameValuePair("ProductID", ProductID));
            params.add(new BasicNameValuePair("CategoryID", categoryID));

            String  error_text = null;
            JSONObject json = jsonParser.makeHttpRequest(url_get_user, "POST", params);
            // check for success tag

            int success = 0;

            // Checking for SUCCESS TAG
            try {
                success = json.getInt(TAG_SUCCESS);
            } catch (Exception e) {
                Log.d("AddProduct_Service", " not working");
            }
            Log.d("Success is:", Integer.toString(success));

            if (success == 1) {
                // successfully created product
                Log.d("Sucess", "Product Addd!");


            } else {

                // failed to create product
                Log.d("failed", "Product_Service not added!");
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

        }
    }
}
