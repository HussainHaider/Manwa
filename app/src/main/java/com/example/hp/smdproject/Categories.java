package com.example.hp.smdproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Categories extends AppCompatActivity {

    GridView gridview;
    private Categories_custom_adapter C1;
    DataBaseAdpter helper;
    List<String> list1;
    List<Productclass> mProductlist;
    String sessionId;




    JSONArray products = null;
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    private static String url_all_gameName = "https://stopshop321.000webhostapp.com/getProductdetail.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_GAME_NAME = "productdetails";
    private static final String TAG_ID = "ID";
    private static final String TAG_pID = "PID";
    private static final String TAG_Price = "Price";
    private static final String TAG_Description = "Description";
    private static final String TAG_Size = "size";


    public static int[] osImages = {
            R.mipmap.jeans,
            R.mipmap.pents,
            R.mipmap.suit,
            R.mipmap.shoe,
//            R.mipmap.shirt,
//            R.mipmap.accessories,
           };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
//        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        helper=new DataBaseAdpter(this);


        list1 = new ArrayList<String>();
        mProductlist = new ArrayList<Productclass>();
        CreateUi();



        final ImageButton imgb = (ImageButton) findViewById(R.id.user_profile_photo);
        final ImageView imgp = (ImageView) findViewById(R.id.header_cover_image);

//        setSupportActionBar(myToolbar);
        gridview = (GridView) findViewById(R.id.customgrid);


        Log.d("get","do something");
        C1=new Categories_custom_adapter(this, mProductlist, osImages);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

//                long v=C1.getItemId(position);
                long v=C1.getlistid(position);
                sessionId= Long.toString(v);

                Toast.makeText(getApplicationContext(),"Item is clicked no. "+v,Toast.LENGTH_SHORT).show();

//                new LoadAllProductdetailName().execute();
                Intent intent = new Intent(getBaseContext(),ItemList.class);
                intent.putExtra("EXTRA_SESSION_ID", sessionId);
                startActivity(intent);

            }
        });

        gridview.setAdapter(C1);


    }
    public void CreateUi()
    {
        list1= helper.gettable2();
        for(int i=0;i<list1.size();i++) {
            if(i%2!=0)
            {
                int result = Integer.parseInt(list1.get(i-1));
                Log.d("get1",list1.get(i-1));
                Log.d("get2",list1.get(i));
                mProductlist.add(new Productclass(result,list1.get(i)));
            }
        }
        return;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        return true;
    }
    public void addproductName(String id,String pid,String D,String S,String P)
    {
        long id1=helper.inserttable5(id,pid,S,D,P);
        String numberAsString = Long.toString(id1);
        Log.d("Data inserted",numberAsString);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    class LoadAllProductdetailName extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Categories.this);
            pDialog.setMessage("Loading data. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("product_id",sessionId));

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


                        Log.d("Userid",id);
                        Log.d("Usernpid",pid);
                        Log.d("UserDescription",Description);
                        Log.d("Userprice",price);
                        Log.d("Usersize",size);

                        addproductName(id,pid,Description,price,size);

//                        Intent intent = new Intent(getBaseContext(),ItemList.class);
//                        intent.putExtra("EXTRA_SESSION_ID", sessionId);
//                        startActivity(intent);
                    }
                } else {
                    Log.d("no Productitems","found");
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
            pDialog.dismiss();
            // updating UI from Background Thread

        }
    }

}
