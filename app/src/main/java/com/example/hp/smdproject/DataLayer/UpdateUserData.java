package com.example.hp.smdproject.DataLayer;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.hp.smdproject.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hussain on 25-Nov-17.
 */

public class UpdateUserData {

    ProgressDialog myDialog;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    JSONArray Users = null;
    private static String url_get_user = "https://stopshop321.000webhostapp.com/deleteUser.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String email=null;
    List<String> UserList=null;

    public void DeleteUSer(List<String> mUserList, Context c)
    {
        UserList=new ArrayList<>();
        UserList=mUserList;

        Log.d("remove","Function()");
       // myDialog= DialogsUtils.showProgressDialog(c,"Checking with Server");
        new DeleteUser().execute();
        //myDialog.dismiss();
    }


    class DeleteUser extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            Log.d("UserLIst"," IN");
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            for(int i=0;i<UserList.size();i++)
            {

                params.add(new BasicNameValuePair("user_email",UserList.get(i)));
                String email=null,error_text=null;
                JSONObject json = jsonParser.makeHttpRequest(url_get_user,
                        "GET", params);
                // check for success tag

                int success=0;

                // Checking for SUCCESS TAG
                try{
                    success = json.getInt(TAG_SUCCESS);
                }catch (Exception e)
                {
                    Log.d("UserLIst"," not working");
                }
                Log.d("Success is:",Integer.toString(success));

                if (success == 1) {
                    // successfully created product
                    Log.d("Sucess","detele USer LIst!");

                } else {

                    // failed to create product
                    Log.d("failed","Deleting USer LIst!");
                    try {
                        error_text= (String) json.get(TAG_MESSAGE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("LOss",error_text);
                }
            }


            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once don
        }
    }


}
