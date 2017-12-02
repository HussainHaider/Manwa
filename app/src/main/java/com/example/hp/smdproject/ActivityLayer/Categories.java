package com.example.hp.smdproject.ActivityLayer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;

import com.example.hp.smdproject.BuniessLayer.Productclass;
import com.example.hp.smdproject.DataLayer.DataBaseAdpter;
import com.example.hp.smdproject.JSONParser;
import com.example.hp.smdproject.R;
import com.example.hp.smdproject.adapter.Categories_custom_adapter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Categories extends AppCompatActivity {

    GridView gridview;
    private Categories_custom_adapter C1;
    DataBaseAdpter helper;
    List<String> list1;
    List<Productclass> mProductlist;
    String sessionId;

    String _id;


    JSONArray products = null;
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    private static String url_all_Name = "https://stopshop321.000webhostapp.com/getProductdetail2image.php";
    private static final String TAG_IMAGE = "image";
    private static final String TAG_GAME_IMAGE = "productdetailimage";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ID = "ID";

    String Category_id;
    public static int[] osImages = {
            R.mipmap.jeans,
            R.mipmap.pents,
            R.mipmap.suit,
            R.mipmap.shoe,
 //           R.mipmap.shirt,
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
        Category_id = getIntent().getStringExtra("EXTRA_SESSION_ID");

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


                String name=C1.getlistname(position);
                Log.d("Name of Product",name);
                long v=C1.getlistid(position);
                sessionId= Long.toString(v);

//                Toast.makeText(getApplicationContext(),"Item is clicked no. "+name,Toast.LENGTH_SHORT).show();

               new LoadProductImage().execute();
                Intent intent = new Intent(getBaseContext(),ItemList.class);
                intent.putExtra("EXTRA_SESSION_ID", sessionId);
                intent.putExtra("EXTRA_SESSION_NAME", name);
                startActivity(intent);
            //    pDialog.dismiss();

            }
        });

        gridview.setAdapter(C1);

    }
    public void CreateUi()
    {
        Bitmap B;
        Log.d("CreateUI","1");
        list1= helper.gettable2(Category_id);
        for(int i=0;i<list1.size();i++) {
            Log.d("CreateUI","2_1");
            if(i%2!=0)
            {
                Log.d("CreateUI","2_2");
                int result = Integer.parseInt(list1.get(i-1));
                Log.d("get1",list1.get(i-1));
                Log.d("get2",list1.get(i));

                B=helper.get2_Image(list1.get(i-1));

                mProductlist.add(new Productclass(result,list1.get(i),B));
            }
        }
        return;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        boolean Flag=true;
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                C1.getFilter().filter(newText);

                Log.i("well", " this worked");
                return false;
            }
        });
        return Flag;
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

    public void addproductImage(String id, Bitmap B1) {
        long id1 = helper.inserttable5_2(id,B1);
        String numberAsString = Long.toString(id1);
        Log.d("Data inserted5_2", numberAsString);
    }

    class LoadProductImage extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Categories.this);
            pDialog.setMessage("Checking from server...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
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
                int success=0;
                // Checking for SUCCESS TAG
                try{
                    success = json.getInt(TAG_SUCCESS);
                }catch (Exception e)
                {
                    Log.d("Service","Product Image Service not working");
                }

                if (success == 1)
                {
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

                        String urldisplay = image;
                        Bitmap mIcon11 = null;
                        try {
                            InputStream in = new java.net.URL(urldisplay).openStream();
                            mIcon11 = BitmapFactory.decodeStream(in);

                        } catch (Exception e) {
                            Log.e("Error", e.getMessage());
                            e.printStackTrace();
                        }

                        addproductImage(_id,mIcon11);


//                        callimagetask(image);

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

            pDialog.dismiss();

        }
    }

}
