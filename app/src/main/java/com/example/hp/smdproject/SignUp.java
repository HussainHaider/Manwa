package com.example.hp.smdproject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SignUp extends AppCompatActivity {
    EditText etName,etEmail,etPassword,etCPassword;
    private ProgressDialog pDialog;
    private String name,email,password,cPassword;
    JSONParser jsonParser = new JSONParser();
    // url to create new product
    private static String url_create_product = "https://stopshop321.000webhostapp.com/add_new_user.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etName=(EditText) findViewById(R.id.name_signup);
        etEmail=(EditText) findViewById(R.id.email_signup);
        etPassword=(EditText) findViewById(R.id.password_signup);
        etCPassword=(EditText) findViewById(R.id.confirm_password);
        Button b= (Button) findViewById(R.id.btn_sginup);
        assert b != null;
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=etName.getText().toString();
                email=etEmail.getText().toString();
                Log.d("On Create","String");
                password=etPassword.getText().toString();
                cPassword=etCPassword.getText().toString();
                if (password.equals(cPassword)) {
                    Log.d("On Create","password");
                    new CreateNewUser().execute();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
//        pDialog.dismiss();
    }

    class CreateNewUser extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SignUp.this);
            pDialog.setMessage("Uploading to server...");
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
            params.add(new BasicNameValuePair("user_name", name));
            params.add(new BasicNameValuePair("user_email", email));
            params.add(new BasicNameValuePair("user_password",password));
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);


                if (success == 1) {
                    // successfully created product
                    Log.d("success","is here!");
                    // closing this screen
                    finish();
                } else {
                    // failed to create product
                    Log.d("failed","is here!");
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
