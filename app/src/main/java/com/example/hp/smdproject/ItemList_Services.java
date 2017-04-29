package com.example.hp.smdproject;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 28-Apr-17.
 */
public class ItemList_Services extends Service {

    JSONArray products = null;
    //    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    private static String url_all_gameName = "https://stopshop321.000webhostapp.com/getProductdetail2.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_GAME_NAME = "productdetails";
    private static final String TAG_ID = "ID";
    private static final String TAG_pID = "PID";
    private static final String TAG_Price = "Price";
    private static final String TAG_Description = "Description";
    private static final String TAG_Size = "size";
    String Category_id;

    DataBaseAdpter Helper;

    String _id;

    private static String url_all_Name = "https://stopshop321.000webhostapp.com/getProductdetail2image.php";
    private static final String TAG_IMAGE = "img";
    private static final String TAG_GAME_IMAGE = "productdetailimage";



    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        Log.d("Item_LIST", "ACTIVTY");
        Toast.makeText(this, "Notifying Start Service", Toast.LENGTH_LONG).show();
        return null;
    }

    @Override
    public int onStartCommand(Intent pIntent, int flags, int startId) {
        // TODO Auto-generated method stub
        Category_id = pIntent.getStringExtra("Category_id");
        Log.d("ID is:",Category_id);
        Toast.makeText(this, "Notifying ItemList Service", Toast.LENGTH_LONG).show();
        Helper = new DataBaseAdpter(this);
        callaysnc();
        return super.onStartCommand(pIntent, flags, startId);
    }

    public void callaysnc() {
        new LoadAllProductdetailName().execute();
    }

    public void addproductName(String id, String pid, String D, String S, String P) {
        long id1 = Helper.inserttable5(id, pid, S, D, P);
        String numberAsString = Long.toString(id1);
        Log.d("Data inserted5", numberAsString);
    }

    class LoadAllProductdetailName extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("Productdetail","Start");
        }

        /**
         * getting All products from url
         */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Category_id", Category_id));

            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_gameName, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Patients: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of patients
                    products = json.getJSONArray(TAG_GAME_NAME);

                    // looping through All Patients
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        // Storing each json item in variable
                        String id = c.getString(TAG_ID);
                        String pid = c.getString(TAG_pID);
                        String Description = c.getString(TAG_Description);
                        String price = c.getString(TAG_Price);
                        String size = c.getString(TAG_Size);


                        Log.d("product_id", id);
                        Log.d("product_pid", pid);
                        Log.d("product_Description", Description);
                        Log.d("product_price", price);
                        Log.d("product_size", size);

                        addproductName(id, pid, Description, price, size);


                    }
                } else {
                    Log.d("no Productitems", "found");
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
            Log.d("Productdetail","END");
            // updating UI from Background Thread
            new LoadProductImage().execute();

        }
    }

    public void addproductImage(String id, Bitmap B1) {
        long id1 = Helper.inserttable5_2(id,B1);
        String numberAsString = Long.toString(id1);
        Log.d("Data inserted5_2", numberAsString);
    }

    public void callimagetask(String a) {
        new DownloadImageTask()
                .execute(a);
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        public DownloadImageTask() {
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            Log.d("Getting","Image");
            addproductImage(_id,result);
        }
    }

    class LoadProductImage extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * getting All products from url
         */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Category_id", Category_id));

            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_Name, "GET", params);


            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of patients
                    products = json.getJSONArray(TAG_GAME_IMAGE);

                    // looping through All Patients
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        // Storing each json item in variable
                        _id = c.getString(TAG_ID);
                        Log.d("product_id1", _id);
                        String image = c.getString(TAG_IMAGE);
                        Log.d("IMAGE_URL", image);


                        callimagetask(image);

                    }
                } else {
                    Log.d("no Product", "found");

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
            // updating UI from Background Thread

        }
    }

}




