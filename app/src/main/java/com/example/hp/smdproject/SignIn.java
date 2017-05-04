package com.example.hp.smdproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SignIn extends AppCompatActivity {
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    // url to create new product
    JSONArray products = null;
    private static String url_create_product = "https://stopshop321.000webhostapp.com/login_check2.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_GAME_NAME = "User_detail";
    private static final String TAG_ID = "ID";
    private static final String TAG_NAME = "Name";
    private static final String TAG_ADDRESS = "Address";
    private static final String TAG_COUNTRY = "Country";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_CC = "CC";

    private String email,password;
    private EditText etEmail,etPassword;
    User U1=new User();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        etEmail=(EditText) findViewById(R.id.etEmail);
        etPassword=(EditText) findViewById(R.id.etPassword);
    }

    public void button(View v)
    {
        if(R.id.sginin==v.getId())
        {
            email=etEmail.getText().toString();
            Log.d("Button","Click");
            password=etPassword.getText().toString();
            new LoginUser().execute();
        }
        if(R.id.createaccount==v.getId())
        {
            Intent i=new Intent(this,SignUp.class);
            startActivity(i);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
//        pDialog.dismiss();
    }

    class LoginUser extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SignIn.this);
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
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("user_email", email));
            params.add(new BasicNameValuePair("user_password",password));
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "GET", params);
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

            //    String type = json.getString(TAG_TYPE);
                if (success == 1) {
                    // successfully created product
                    Log.d("success","is here!");

                    products = json.getJSONArray(TAG_GAME_NAME);

                    // looping through All Patients
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        // Storing each json item in variable
                        String id = c.getString(TAG_ID);
                        String name = c.getString(TAG_NAME);
                        String address = c.getString(TAG_ADDRESS);
                        String country = c.getString(TAG_COUNTRY);
                        String email = c.getString(TAG_EMAIL);
                        String CC = c.getString(TAG_CC);

                        Log.d("user_id", id);
                        Log.d("user_name", name);
                        Log.d("user_address", address);
                        Log.d("user_country", country);
                        Log.d("user_email", email);
                        Log.d("user_CC", CC);


                        SharedPreferences sharedPreferences=getSharedPreferences("MyData", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("User_ID",id);
                        editor.putString("user_NAME",name);
                        editor.putString("user_ADDRESS",address);
                        editor.putString("user_COUNTRY",country);
                        editor.putString("user_EMAIL",email);
                        editor.putString("user_CC",CC);

                        editor.commit();

                        U1.ID=Integer.parseInt(id);
                        U1.Name=name;

                        U1.Address=address;
                        U1.Country=country;
                        if(CC.isEmpty() && CC!=null ) {
                            U1.Creidt_Card = Integer.parseInt(CC);
                        }
                        else
                        {
                            U1.Creidt_Card=0;
                        }


                    }




                    Intent intent = new Intent(getApplicationContext(), User_Profile_Activity.class);
                   // intent.putExtra("UserObject",U1);
                    intent.putExtra("user", U1);
                    startActivity(intent);
                    // closing this screen
                    finish();
                } else {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }
}

