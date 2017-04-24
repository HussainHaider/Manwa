package com.example.hp.smdproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SignIn extends AppCompatActivity {
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    // url to create new product
    private static String url_create_product = "https://stopshop321.000webhostapp.com/login_check.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_TYPE = "type";
    private String email,password;
    private EditText etEmail,etPassword;

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
        pDialog.dismiss();
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
                    Intent intent = new Intent(getApplicationContext(), Show_items.class);
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

