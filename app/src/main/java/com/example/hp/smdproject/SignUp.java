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
import android.widget.TextView;
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
    public TextView etext;
    private ProgressDialog pDialog;
    public Button b;
    DataBaseAdpter helper;
    int UserID=0;
    List<String> list1;
    private String name,email,password,cPassword,error_text;
    JSONParser jsonParser = new JSONParser();
    // url to create new product
    private static String url_create_product = "https://stopshop321.000webhostapp.com/add_new_user.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

            helper=new DataBaseAdpter(this);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sign_up);
            etName=(EditText) findViewById(R.id.name_signup);
            etEmail=(EditText) findViewById(R.id.email_signup);
            etPassword=(EditText) findViewById(R.id.password_signup);
            etCPassword=(EditText) findViewById(R.id.confirm_password);
            etext=(TextView)findViewById(R.id.error_id1);
        assert etext != null;
        etext.setVisibility(View.GONE);
            list1 = new ArrayList<String>();

            list1= helper.gettable1();
            if(list1.size()>0)
                UserID=Integer.parseInt(list1.get(list1.size() - 1));





//        assert b != null;
//            b= (Button) findViewById(R.id.btn_sginup);


    }
    public void button(View v)
    {
        name=etName.getText().toString();
        email=etEmail.getText().toString();
        Log.d("On Create","String");
        boolean flag=true;
        password=etPassword.getText().toString();
        cPassword=etCPassword.getText().toString();

        if(!isValidEmailAddress(email))
        {
            etEmail.setError("Give Correct input!");
            flag=false;

        }
        else if(password.length()<8)
        {
            etPassword.setError("Password Should be 8 characters atleast");
            flag=false;
        }
        if(!isUpperCase(password))
        {
            etPassword.setError("At least 1 Capital latter!");
            flag=false;
        }
        if(!isNumeric(password))
        {
            etPassword.setError("At least 1 Digit!");
            flag=false;
        }
        if(name.isEmpty() || email.isEmpty() || password.isEmpty() || cPassword.isEmpty())
        {
            Toast.makeText(this, "Add Complete info", Toast.LENGTH_LONG).show();
            flag=false;
        }
        if(flag==true)
        {
            if (password.equals(cPassword)) {
                Log.d("On Create", "password");
                new CreateNewUser().execute();
            }
            else {
                Toast.makeText(this, "Password & Cpassword should be same info", Toast.LENGTH_LONG).show();
            }
        }
    }

    public static boolean isUpperCase(String s)
    {
        boolean flag=false;
        for (int i=0; i<s.length(); i++)
        {
            if (Character.isUpperCase(s.charAt(i)))
            {
                return true;
            }
        }
        return false;
    }
    public static boolean isNumeric(String str)
    {
        for (int i=0; i<str.length(); i++)
        {
            if (Character.isDigit(str.charAt(i)))
            {
                return true;
            }
        }
        return false;
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
            UserID++;
            String numberAsString = Integer.toString(UserID);

            params.add(new BasicNameValuePair("user_name", name));
            params.add(new BasicNameValuePair("user_email", email));
            params.add(new BasicNameValuePair("user_password",password));
            params.add(new BasicNameValuePair("user_id",numberAsString));
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);

            // check for success tag

            int success=0;

            // Checking for SUCCESS TAG
            try{
                success = json.getInt(TAG_SUCCESS);
            }catch (Exception e)
            {
                Log.d("Signup"," not working");
            }
            Log.d("Success is:",Integer.toString(success));

            if(success==0)
            {

                try {
                    error_text= (String) json.get(TAG_MESSAGE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("LOss",error_text);


            }



            else if (success == 1) {
                // successfully created product
                Log.d("success5","is here!");
                // closing this screen

                SharedPreferences sharedPreferences=getSharedPreferences("MyData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("User_ID",numberAsString);
                editor.putString("user_NAME",name);
                editor.putString("user_ADDRESS","");
                editor.putString("user_COUNTRY","");
                editor.putString("user_EMAIL",email);
                editor.putString("user_CC","");

                editor.commit();
                Intent intent = new Intent(getApplicationContext(), User_Profile_Activity.class);
                // intent.putExtra("UserObject",U1);
                //    intent.putExtra("user", U1);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                finish();
            } else {

                // failed to create product
                Log.d("failed","Sign up!");
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once don
            pDialog.dismiss();
            if(error_text.contains("Email")){
                etext.setVisibility(View.VISIBLE);
            }
        }
    }
}
