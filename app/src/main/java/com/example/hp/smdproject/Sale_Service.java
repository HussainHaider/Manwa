package com.example.hp.smdproject;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
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
 * Created by HP on 03-May-17.
 */
public class Sale_Service extends Service {

    JSONArray products = null;
    //    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    private static String url_all_gameName = "https://stopshop321.000webhostapp.com/getSaleProduct.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_GAME_NAME = "Specailproduct";
    private static final String TAG_GID = "OfferID";
    private static final String TAG_NAME = "Name";
    private static final String TAG_Price = "Price";
    private static final String TAG_Description = "Description";
    private static final String TAG_IMG = "img";

    DataBaseAdpter Helper;
    private String[] Saleimages;


    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent pIntent, int flags, int startId) {
        // TODO Auto-generated method stub
        Toast.makeText(this, "Notifying Sale Service", Toast.LENGTH_LONG).show();
        Helper=new DataBaseAdpter(this);
        Saleimages= new String[6];

        callaysnc();
//        for(int i=0;i<Saleimages.length;i++)
//        {
//            String urldisplay = Saleimages[i];
//            Bitmap mIcon11 = null;
//            try {
//                InputStream in = new java.net.URL(urldisplay).openStream();
//                mIcon11 = BitmapFactory.decodeStream(in);
//
//            } catch (Exception e) {
//                Log.e("Error", e.getMessage());
//                e.printStackTrace();
//            }
//            addSproductImage(i+1,mIcon11);
//        }


        return super.onStartCommand(pIntent, flags, startId);
    }
    public void addSproductImage(int id,Bitmap n)
    {
        String numberAsString = Integer.toString(id);
//        long id1=Helper.inserttable10_2(numberAsString,n);
//        String numberAsString2 = Long.toString(id1);
//        Log.d("Data inserted10",numberAsString2);
    }

    public void callaysnc()
    {

        Log.d("Sale","Service");
        new LoadAllProductName().execute();
    }
    public void addSaleproduct(String id,String n,String D,String P)
    {
        long id1=Helper.inserttable10(id,n,D,P);
        String numberAsString = Long.toString(id1);
        Log.d("Data inserted10",numberAsString);
    }
    class LoadAllProductName extends AsyncTask<String, String, String> {

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
            params.add(new BasicNameValuePair("Category_id","2"));

            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_gameName, "GET", params);

            // Check your log cat for JSON reponse
//            Log.d("All Patients: ", json.toString());
            Log.d("Sale","Service2");
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of patients
                    products = json.getJSONArray(TAG_GAME_NAME);
                    Log.d("Sale","Service3");
                    // looping through All Patients
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);
                        Log.d("Sale","Service4");
                        // Storing each json item in variable

                        String id = c.getString(TAG_GID);
                        String name = c.getString(TAG_NAME);
                        String Description = c.getString(TAG_Description);
                        String price = c.getString(TAG_Price);
                        String image = c.getString(TAG_IMG);

                        Log.d("Special_Product_id",id);
                        Log.d("Special_Product_name",name);
                        Log.d("Special_p_Description", Description);
                        Log.d("Special_product_price", price);
                        Log.d("Special_IMAGE_URL", image);
                        Saleimages[i]=image;
                        addSaleproduct(id,name,Description,price);

                    }
                } else {
                    Log.d("no Special_Product","found");
                    Log.d("Sale","Service5");

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
