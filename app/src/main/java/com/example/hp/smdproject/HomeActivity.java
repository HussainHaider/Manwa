package com.example.hp.smdproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ViewPager viewPager = null;
    View headerview ;
    private testing test;
    int btnid;

    JSONArray products = null;
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    private static String url_all_gameName = "https://stopshop321.000webhostapp.com/getProduct.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_GAME_NAME = "ProductNames";
    private static final String TAG_GID = "ID";
    private static final String TAG_NAME = "Name";

    DataBaseAdpter Helper;
    String categoryid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.pager);
        Log.d("Helper","class1");
        Helper=new DataBaseAdpter(this);
        Log.d("Helper","class");
        categoryid="";btnid=1;

        HomeActivity.ImagePagerAdapter adapter = new HomeActivity.ImagePagerAdapter();
        viewPager.setAdapter(adapter);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);
        Button B1=(Button)headerview.findViewById(R.id.joinbtn);
        Button B2=(Button)headerview.findViewById(R.id.signinbtn);
        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "clicked1", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });
        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "clicked2", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), SignIn.class);
                startActivity(intent);
            }
        });
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_homebtn) {
            Log.d("Camera","option");
            Intent intent = new Intent(getApplicationContext(), Show_items.class);
            startActivity(intent);
            // Handle the camera action

        } else if (id == R.id.nav_menshop) {
            Log.d("Gallery","option");
            getApplicationContext().startService(new Intent(getApplicationContext(), ItemList_Services.class));
//            test=new testing();
//            test.setContext(getApplication());
//            test.loadData();
            categoryid="1";
//            callaysnc();
//            new LoadAllProductName().execute();


            Intent intent = new Intent(getApplicationContext(), Categories.class);
            startActivity(intent);

        } else if (id == R.id.nav_womenshop) {
            categoryid="2";
//            callaysnc();

            Log.d("slideshow","option");

        } else if (id == R.id.nav_manage) {
            Log.d("Manage","option");

        } else if (id == R.id.nav_share) {
            Log.d("Share","option");

        } else if (id == R.id.nav_send) {
            Log.d("Send","option");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private class ImagePagerAdapter extends PagerAdapter
    {
        private int[] mImages = new int[]{
                R.drawable.a,
                R.drawable.b,
                R.drawable.c,
                R.drawable.d,
                R.drawable.e,
        };

        private String[] title = {
                "Men",
                "Women",
                "Kids",
                "Discipline",
                "Drug Addiction"
        };


        @Override
        public int getCount() {
            return mImages.length;
        }

        //        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {


            Context context = HomeActivity.this;
            View v;
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.fragmentfinal, container, false);
            LinearLayout f= (LinearLayout) v.findViewById(R.id.layoutinner);
            f.setBackgroundResource(mImages[position]);


            TextView text = (TextView) v.findViewById(R.id.layouttext);
            text.setText(title[position]);
            text.setId(position);
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast toast = Toast.makeText(getApplicationContext(),"clicking"+v.getId(),Toast.LENGTH_LONG);
                    toast.show();


                }
            });
            btnid++;

            container.addView(v);

            return v;

        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((LinearLayout) object);
        }
    }
    public void callaysnc()
    {
//        new LoadAllProductName().execute();
    }
    public void addproductName(String id,String n)
    {
        long id1=Helper.inserttable2(id,n);
        String numberAsString = Long.toString(id1);
        Log.d("Data inserted",numberAsString);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        pDialog.dismiss();
    }


    class LoadAllProductName extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(HomeActivity.this);
            pDialog.setMessage("Loading data. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("category_id",categoryid));

            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_gameName, "GET", params);

            // Check your log cat for JSON reponse
//            Log.d("All Patients: ", json.toString());

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
                        Log.d("Userid",id);
                        Log.d("Username",name);

                        addproductName(id,name);

                        Intent intent = new Intent(getApplicationContext(), Categories.class);
                        startActivity(intent);
                    }
                } else {
                    Log.d("no Product","found");
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
            pDialog.dismiss();
            // updating UI from Background Thread

        }
    }


}
