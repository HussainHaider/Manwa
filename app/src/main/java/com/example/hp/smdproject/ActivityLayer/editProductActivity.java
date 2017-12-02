package com.example.hp.smdproject.ActivityLayer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.hp.smdproject.DataLayer.DataBaseAdpter;
import com.example.hp.smdproject.JSONParser;
import com.example.hp.smdproject.R;
import com.example.hp.smdproject.adapter.Userinfo_adapter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class editProductActivity extends AppCompatActivity {


    String Category_id=null;
    private Userinfo_adapter adapter;
    private ListView lvProduct;
    DataBaseAdpter data=null;
    private List<String> ListofProduct;
    private List<String> productlist;
    TextView txt;

    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    JSONArray Users = null;
    private static String url_get_user = "https://stopshop321.000webhostapp.com/deleteProduct.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    boolean checkflag=false;
    String ID=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        Category_id = getIntent().getStringExtra("Category_ID2");
        ListofProduct = new ArrayList<>();

        productlist = new ArrayList<>();
        lvProduct=(ListView)findViewById(R.id.Productlist);
        data=new DataBaseAdpter(editProductActivity.this);
        Log.d("Category_id",Category_id);
        productlist=data.getalltable2(Category_id);
        txt=(TextView)findViewById(R.id.error_txt);
        txt.setVisibility(View.INVISIBLE);

        adapter=new Userinfo_adapter(editProductActivity.this,productlist);
        lvProduct.setAdapter(adapter);

    }
    public void ProductUpgradition(View v)
    {
        if(v.getId()==R.id.btndeleteProduct)
        {
            new DeleteProduct().execute();
        }
        if(v.getId()==R.id.btnsEditProduct)
        {
            if(ListofProduct.size()==1)
            {
                String name=ListofProduct.get(0);
                ID=data.getallIDtable2(name);

                Intent intent = new Intent(getApplicationContext(), editProductDetailActivity.class);
                intent.putExtra("Product_ID", ID);
                intent.putExtra("Product_Name",name);
                startActivity(intent);
            }
            else
            {
                txt.setVisibility(View.VISIBLE);
            }
        }
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
                adapter.getFilter().filter(newText);

                Log.i("well", " this worked");
                return false;
            }
        });
        return Flag;
    }
    public void insertListData(String text)
    {
        Log.d("Data","added");
        ListofProduct.add(text);
    }
    public void deleteListData(String text)
    {
        Log.d("Data","remove");
        ListofProduct.remove(text);
    }
    public  void deleteProduct(String PID,String CID)
    {
        data.Deletetable5(PID);
        data.Deletetable7(PID,CID);
        data.Deletetable2(PID);
    }

    class DeleteProduct extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(editProductActivity.this);
            pDialog.setMessage("Checking from server...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            Log.d("UserLIst"," IN");

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            for(int i=0;i<ListofProduct.size();i++) {

                String name=ListofProduct.get(i);
                ID=data.getallIDtable2(name);

                params.add(new BasicNameValuePair("ID", ID));
                params.add(new BasicNameValuePair("Category_ID", Category_id));

                String email = null, error_text = null;
                JSONObject json = jsonParser.makeHttpRequest(url_get_user,
                        "GET", params);
                // check for success tag

                int success = 0;

                // Checking for SUCCESS TAG
                try {
                    success = json.getInt(TAG_SUCCESS);
                } catch (Exception e) {
                    Log.d("UserLIst", " not working");
                }
                Log.d("Success is:", Integer.toString(success));

                if (success == 1) {
                    // successfully created product
                    checkflag = true;

                } else {

                    // failed to create product
                    Log.d("failed", "Getting USer LIst!");
                    try {
                        error_text = (String) json.get(TAG_MESSAGE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("LOss", error_text);
                }
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once don
            pDialog.dismiss();
            deleteProduct(ID,Category_id);
            finish();
        }
    }
}
