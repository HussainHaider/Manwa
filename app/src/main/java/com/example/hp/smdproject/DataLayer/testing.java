package com.example.hp.smdproject.DataLayer;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.hp.smdproject.BuniessLayer.User;
import com.example.hp.smdproject.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 20-Apr-17.
 */
public class testing {
    private ArrayList<User> gameNames;
    private ProgressDialog pDialog;
    JSONArray products = null;
//    private Controller controller;
    JSONParser jParser = new JSONParser();
    private static String url_all_gameName = "https://stopshop321.000webhostapp.com/test.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_GAME_NAME = "UserNames";
    private static final String TAG_GID = "ID";
    private static final String TAG_NAME = "Name";
    private static final String TAG_email = "email";

//    private static Controller ourInstance = new Controller();
    private Context context;


    public static testing getInstance() {
        return null;
  //      return ourInstance;
    }
    public void setContext(Context c){
        context=c;
    }
    public void loadData(){
        new LoadAllGameName().execute();
    }

    public testing() {
        gameNames =new ArrayList<>();
    }

    public void addGameName(String id, String name, String imgId) {
        gameNames.add(new User(id,name,imgId));
    }
//    public void dimsssdialog()
//    {
//        pDialog.dismiss();
//    }



    public ArrayList<User> getGameName() {
        return gameNames;
    }

    class LoadAllGameName extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pDialog = new ProgressDialog(context);
//            pDialog.setMessage("Loading data. Please wait...");
//            pDialog.setIndeterminate(false);
//            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            pDialog.setCancelable(false);
//            pDialog.show();
        }

        /**
         * getting All products from url
         */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("user_id","4"));

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
                        String id = c.getString(TAG_GID);
                        String name = c.getString(TAG_NAME);
                        String email = c.getString(TAG_email);
                        Log.d("Userid",id);
                        Log.d("Username",name);
                        Log.d("Useremail",email);

                        addGameName(id,name,email);
                    }
                } else {
                    Log.d("no user","found");
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
            //pDialog.dismiss();
            // updating UI from Background Thread

        }
    }

}
