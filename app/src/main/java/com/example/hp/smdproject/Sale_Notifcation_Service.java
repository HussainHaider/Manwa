package com.example.hp.smdproject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
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
public class Sale_Notifcation_Service extends Service {
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
    List<String> list1;
    int result2;

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
        list1 = new ArrayList<String>();
        result2=0;
        CreateUI();
        callaysnc();
        return super.onStartCommand(pIntent, flags, startId);
    }
    public void callaysnc()
    {
        Log.d("Sale","Service");
        new LoadAllProductName().execute();
    }
    public void addSaleproduct(String id,String n,String D,String P)
    {
        NotificationCompat.Builder builder=new NotificationCompat.Builder(Sale_Notifcation_Service.this);
        builder.setContentTitle("Check my Notification");
        builder.setContentText("Actullay I want this Notification in the Srevice ID:"+id);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setTicker("Hey do u want the Sale");
        builder.setAutoCancel(true);

//        builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);

        Intent i=new Intent(Sale_Notifcation_Service.this,SignIn.class);
        TaskStackBuilder stackBuilder=TaskStackBuilder.create(this);
        stackBuilder.addParentStack(SignIn.class);
        stackBuilder.addNextIntent(i);
        PendingIntent pi_main=stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pi_main);


        Notification notification= builder.build();
        NotificationManager manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1234,notification);
    }
    public void CreateUI()
    {
        int j=1;
        list1= Helper.gettable10();
        Bitmap B;
        for(int i=0;i<list1.size();i++) {
            if(j%4==0 && i!=0)
            {
                int result1=0;
                String des="";

                result2 = Integer.parseInt(list1.get(i-3));
                Log.d("get4_1",list1.get(i-3));




                Log.d("get3_1",list1.get(i-2));
                Log.d("get2_1",list1.get(i-1));
                Log.d("get1_1",list1.get(i));


            }
            j++;
        }
        String numberAsString = Integer.toString(result2);
        Log.d("result2",numberAsString);
        return;
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
//                        Saleimages[i]=image;
                        if(result2<=Integer.parseInt(id))
                        {
                            addSaleproduct(id,name,Description,price);
                            break;
                        }

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
