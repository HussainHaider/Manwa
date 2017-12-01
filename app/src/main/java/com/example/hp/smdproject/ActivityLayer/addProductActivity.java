package com.example.hp.smdproject.ActivityLayer;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.hp.smdproject.JSONParser;
import com.example.hp.smdproject.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class addProductActivity extends AppCompatActivity {


    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    JSONArray products = null;
    private static String url_get_user = "https://stopshop321.000webhostapp.com/add_new_product2.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_GAME_NAME = "product";
    private static final String TAG_GID = "ID";

    EditText E1,E2;
    String imageurl,name,ID=null,category_ID=null;
    boolean flag=true;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        E1= (EditText) findViewById(R.id.imageurl);
        E2= (EditText) findViewById(R.id.productName);

        spinner = (Spinner) findViewById(R.id.productspinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                category_ID=parentView.getItemAtPosition(position).toString();
                Log.d("category_ID",category_ID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }
    public void AddProduct(View v) {

        flag=true;

        if (v.getId() == R.id.addP) {

            Log.d("AddProduct","function_1");
            imageurl = E1.getText().toString();
            if (imageurl.isEmpty()) {
                E1.setError("Give Image Url");
                Log.d("AddProduct","show_false");
                flag = false;
            }
            name = E2.getText().toString();
            if (name.isEmpty()) {
                E2.setError("Give Name");
                Log.d("AddProduct","show_false");
                flag = false;
            }
            Log.d("AddProduct","function_2");
            if(category_ID==null)
            {
                category_ID=spinner.getItemAtPosition(0).toString();
            }


            if(category_ID.equals("Male"))
            {
                category_ID="1";
            }
            else if(category_ID.equals("Female"))
            {
                category_ID="2";
            }
            Log.d("AddProduct","function_3");

            if(flag)
            {
                Log.d("AddProduct","function_4");
                Log.d("AddProduct","show");
                new AddProduct().execute();
            }
        }
    }

    private class AddProduct extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(addProductActivity.this);
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


            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("image", imageurl));
            params.add(new BasicNameValuePair("CategoryID", category_ID));

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

        }
    }

}
