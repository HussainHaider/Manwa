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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    public EditText etName,etEmail,etPassword,etCPassword;
    private ProgressDialog pDialog;
    public Button b;
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
//        assert b != null;
//            b= (Button) findViewById(R.id.btn_sginup);


    }
    public void button(View v)
    {
        name=etName.getText().toString();
        email=etEmail.getText().toString();
        Log.d("On Create","String");
        password=etPassword.getText().toString();
        cPassword=etCPassword.getText().toString();
        if (password.equals(cPassword)) {
            Log.d("On Create", "password");
        }
        if(!isValidEmailAddress(email))
        {
            etEmail.setError("Give Correct input!");

        }
        if(name.isEmpty() || email.isEmpty() || password.isEmpty() || cPassword.isEmpty())
        {
            Toast.makeText(this, "Add Complete info", Toast.LENGTH_LONG).show();
        }
        else{
            new CreateNewUser().execute();
        }
    }
    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email);

        if(mat.matches()){

//            System.out.println("Valid email address");
            return true;
        }else{

//            System.out.println("Not a valid email address");
            return false;
        }
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
                    SharedPreferences sharedPreferences=getSharedPreferences("MyData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("User_ID","4");
                    editor.putString("user_NAME",name);
                    editor.putString("user_ADDRESS","");
                    editor.putString("user_COUNTRY","");
                    editor.putString("user_EMAIL",email);
                    editor.putString("user_CC","");

                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(), User_Profile_Activity.class);
                    // intent.putExtra("UserObject",U1);
                    //    intent.putExtra("user", U1);
                    startActivity(intent);

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
