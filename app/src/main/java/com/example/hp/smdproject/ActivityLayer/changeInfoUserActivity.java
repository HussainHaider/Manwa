package com.example.hp.smdproject.ActivityLayer;

import android.app.ProgressDialog;
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

import com.example.hp.smdproject.DataLayer.UpdateUserData;
import com.example.hp.smdproject.JSONParser;
import com.example.hp.smdproject.R;
import com.example.hp.smdproject.adapter.Userinfo_adapter;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class changeInfoUserActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private List<String> mUserEmailList;
    private List<String> mSuspendUserEmailList;
    private List<String> ListofUser;
    private Userinfo_adapter adapter;
    private ListView lvUser;
    UpdateUserData User;

    JSONParser jsonParser = new JSONParser();
    JSONArray Users = null;
    private static String url_get_user = "https://stopshop321.000webhostapp.com/getUser_info.php";
    private static String url_get_suspenduser = "https://stopshop321.000webhostapp.com/getSuspendUser_info.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_GAME_NAME = "User_detail";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info_user);
        mUserEmailList = new ArrayList<>();
        mSuspendUserEmailList = new ArrayList<>();

        ListofUser = new ArrayList<>();
        lvUser=(ListView)findViewById(R.id.Userlist);
        User=new UpdateUserData();
        new GetUserList().execute();

    }
    public void DataUpgradition(View v)
    {
        if(v.getId()==R.id.btnremove)
        {
            Log.d("remove","button");
            User.DeleteUSer(ListofUser,changeInfoUserActivity.this);
            new GetUserList().execute();
        }
        if(v.getId()==R.id.btnsuspend)
        {
            Log.d("Suspend","button");
            User.SuspendUSer(ListofUser,changeInfoUserActivity.this);
            new GetSuspendUserList().execute();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_suspend) {
            Log.d("Menu2", "SUspend");
            new GetSuspendUserList().execute();

            return true;
        }
        else if (id == R.id.action_delete) {
            Log.d("Menu2", "Delete");
            new GetUserList().execute();
            return true;
        }

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
        ListofUser.add(text);
    }
    public void deleteListData(String text)
    {
        Log.d("Data","remove");
        ListofUser.remove(text);
    }



    class GetUserList extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            lvUser.setAdapter(null);
            mUserEmailList.clear();
            pDialog = new ProgressDialog(changeInfoUserActivity.this);
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
                try {
                    Users = json.getJSONArray(TAG_GAME_NAME);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // looping through All Patients
                for (int i = 0; i < Users.length(); i++) {
                    JSONObject c = null;
                    try {
                        c = Users.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // Storing each json item in variable
                    try {
                        email = c.getString(TAG_EMAIL);
                        mUserEmailList.add(email);
                        Log.d("email_text",email);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            } else {

                // failed to create product
                Log.d("failed","Getting USer LIst!");
                try {
                    error_text= (String) json.get(TAG_MESSAGE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("LOss",error_text);
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once don
            pDialog.dismiss();

            adapter=new Userinfo_adapter(changeInfoUserActivity.this,mUserEmailList);
            lvUser.setAdapter(adapter);
        }
    }


    class GetSuspendUserList extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            lvUser.setAdapter(null);
            mSuspendUserEmailList.clear();
            pDialog = new ProgressDialog(changeInfoUserActivity.this);
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
            String email=null,error_text=null;
            JSONObject json = jsonParser.makeHttpRequest(url_get_suspenduser,
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
                try {
                    Users = json.getJSONArray(TAG_GAME_NAME);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // looping through All Patients
                for (int i = 0; i < Users.length(); i++) {
                    JSONObject c = null;
                    try {
                        c = Users.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // Storing each json item in variable
                    try {
                        email = c.getString(TAG_EMAIL);
                        mSuspendUserEmailList.add(email);
                        Log.d("email_text",email);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            } else {

                // failed to create product
                Log.d("failed","Getting USer LIst!");
                try {
                    error_text= (String) json.get(TAG_MESSAGE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("LOss",error_text);
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once don
            pDialog.dismiss();

            adapter=new Userinfo_adapter(changeInfoUserActivity.this,mSuspendUserEmailList);
            lvUser.setAdapter(adapter);
        }
    }
}
