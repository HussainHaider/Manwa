package com.example.hp.smdproject;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 19-Apr-17.
 */
public class Category_Services extends Service {

    JSONArray products = null;
//    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    private static String url_all_gameName = "https://stopshop321.000webhostapp.com/getProduct2.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_GAME_NAME = "ProductNames";
    private static final String TAG_GID = "ID";
    private static final String TAG_NAME = "Name";

    DataBaseAdpter Helper;

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent pIntent, int flags, int startId) {
        // TODO Auto-generated method stub
        Toast.makeText(this, "Notifying Category Service", Toast.LENGTH_LONG).show();
        Helper=new DataBaseAdpter(this);
        callaysnc();
        return super.onStartCommand(pIntent, flags, startId);
    }
    public void callaysnc()
    {
        Log.d("Category","Service");
        new LoadAllProductName().execute();
    }
    public void addproductName(String id,String n)
    {
        long id1=Helper.inserttable2(id,n);
        String numberAsString = Long.toString(id1);
        Log.d("Data inserted2",numberAsString);
    }
    class LoadAllProductName extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pDialog = new ProgressDialog(HomeActivity.this);
//            pDialog.setMessage("Loading data. Please wait...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(false);
//            pDialog.show();
        }

        /**
         * getting All products from url
         */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("category_id","2"));

            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_gameName, "GET", params);

            // Check your log cat for JSON reponse
//            Log.d("All Patients: ", json.toString());
            Log.d("Category","Service2");
            try {
                int success=0;
                // Checking for SUCCESS TAG
//                success = json.getInt(TAG_SUCCESS);
                try{
                    success = json.getInt(TAG_SUCCESS);
                }catch (Exception e)
                {
                    Log.d("Service","Notifying Category Service not working");
                }


                if (success == 1) {
                    // products found
                    // Getting Array of patients
                    products = json.getJSONArray(TAG_GAME_NAME);
                    Log.d("Category","Service3");
                    // looping through All Patients
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);
                        Log.d("Category","Service4");
                        // Storing each json item in variable
                        String id = c.getString(TAG_GID);
                        String name = c.getString(TAG_NAME);
                        Log.d("Product_id",id);
                        Log.d("Product_name",name);

                        addproductName(id,name);

                    }
                } else {
                    Log.d("no Product","found");
                    Log.d("Category","Service5");
                    // no products found
                    // Launch Add New product Activity
                   /* Intent i = new Intent(getApplicationContext(),
                            NewProductActivity.class);
                    // Closing all previous activities
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);*/
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
//            pDialog.dismiss();
            // updating UI from Background Thread

        }
    }

}