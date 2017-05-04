package com.example.hp.smdproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

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
    private static String url_all_gameName = "https://stopshop321.000webhostapp.com/getProductCategory.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_GAME_NAME = "ProductNames";
    private static final String TAG_PID = "PID";
    private static final String TAG_CID = "CID";
    Button B1,B2;
    DataBaseAdpter Helper;
    String categoryid;

    private AdView mAdView;
    public static final String DEFAULT="N/A";
    String SP_User_ID;
    String SP_User_NAME;
    String SP_User_ADDRESS;
    String SP_User_COUNTRY;
    String SP_User_EMAIL;
    String SP_User_CC;
    Boolean SharedPreferencesFlag;
    SharedPreferences sharedPreferences;
    Button B3;



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
        B3=new Button(getApplicationContext());
        mAdView = (AdView) findViewById(R.id.adView);
//
        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                // Check the LogCat to get your test device ID
//                .addTestDevice("51BC4A7A6F9441A4F3FD979DD2D32F66")
                .build();

        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Toast.makeText(getApplicationContext(), "Ad is loaded!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClosed() {
                Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Toast.makeText(getApplicationContext(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                Toast.makeText(getApplicationContext(), "Ad is opened!", Toast.LENGTH_SHORT).show();
            }
        });

        mAdView.loadAd(adRequest);

        HomeActivity.ImagePagerAdapter adapter = new HomeActivity.ImagePagerAdapter();
        viewPager.setAdapter(adapter);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);

        LinearLayout L1=(LinearLayout)headerview.findViewById(R.id.headerLinear);
        B1=(Button)headerview.findViewById(R.id.joinbtn);
        B2=(Button)headerview.findViewById(R.id.signinbtn);

        sharedPreferences=getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SP_User_ID=sharedPreferences.getString("User_ID",DEFAULT);
        SP_User_NAME=sharedPreferences.getString("user_NAME",DEFAULT);
        SP_User_ADDRESS=sharedPreferences.getString("user_ADDRESS",DEFAULT);
        SP_User_COUNTRY=sharedPreferences.getString("user_COUNTRY",DEFAULT);
        SP_User_EMAIL=sharedPreferences.getString("user_EMAIL",DEFAULT);
        SP_User_CC=sharedPreferences.getString("user_CC",DEFAULT);

        if(SP_User_ID.equals(DEFAULT) || SP_User_NAME.equals(DEFAULT) || SP_User_ADDRESS.equals(DEFAULT) || SP_User_COUNTRY.equals(DEFAULT) || SP_User_EMAIL.equals(DEFAULT) || SP_User_CC.equals(DEFAULT))
        {
            Toast.makeText(this, "SharedPreferences Empty", Toast.LENGTH_LONG).show();
            SharedPreferencesFlag=false;
            B1.setVisibility(View.VISIBLE);
            B2.setVisibility(View.VISIBLE);
        }
        else {
            SharedPreferencesFlag=true;
            B1.setVisibility(View.INVISIBLE);
            B2.setVisibility(View.INVISIBLE);
            B3.setText("Signout");
            B3.setTextColor(getResources().getColor(R.color.theme1));
            B3.setBackground(getResources().getDrawable(R.drawable.mybutton2));

            B3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(HomeActivity.this, "clicked3", Toast.LENGTH_SHORT).show();
                    SharedPreferencesFlag=false;
                    sharedPreferences.edit().clear().commit();

                }
            });
            L1.addView(B3);


        }



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






        Helper.inserttable6("1","Men");
        Helper.inserttable6("2","Female");
        Helper.inserttable6("3","kid");




        callaysnc();

        getApplicationContext().startService(new Intent(getApplicationContext(), Sale_Service.class));
//        getApplicationContext().startService(new Intent(getApplicationContext(), Category_Services.class));



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

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
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
            finish();
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            // Handle the camera action

        } else if (id == R.id.nav_menshop) {
            Log.d("Gallery","option");
            categoryid="1";
            //Service
            Intent i = new Intent(this, ItemList_Services.class);
            i.putExtra("Category_id",categoryid);
            startService(i);

            //Activity
            Intent intent = new Intent(getApplicationContext(), Categories.class);
            intent.putExtra("EXTRA_SESSION_ID", categoryid);
            startActivity(intent);

        } else if (id == R.id.nav_womenshop) {
            categoryid="2";

            //Service
            Intent i = new Intent(this, ItemList_Services.class);
            i.putExtra("Category_id",categoryid);
            startService(i);

            //Activity
            Intent intent = new Intent(getApplicationContext(), Categories.class);
            intent.putExtra("EXTRA_SESSION_ID", categoryid);
            startActivity(intent);

            Log.d("slideshow","option");

        } else if (id == R.id.nav_profile) {
            Log.d("nav_profile","option");
            Intent intent = new Intent(getApplicationContext(), userProfile.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {
            Log.d("Share","option");
            Intent intent = new Intent(getApplicationContext(), Sale_Activity.class);
            startActivity(intent);

        } else if (id == R.id.nav_send) {
            Log.d("Send","option");
            getApplicationContext().startService(new Intent(getApplicationContext(), Sale_Notifcation_Service.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private class ImagePagerAdapter extends PagerAdapter
    {
        private int[] mImages = new int[]{
                R.drawable.men,
                R.drawable.women,
                R.drawable.kids,
//                R.drawable.d,
//                R.drawable.e,
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
        new LoadAllProductCatgory().execute();
    }
    public void addproductCategory(String id,String n)
    {
        long id1=Helper.inserttable7(id,n);
        String numberAsString = Long.toString(id1);
        Log.d("Data inserted",numberAsString);
    }



    class LoadAllProductCatgory extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        /**
         * getting All products from url
         */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Productcategory_id",categoryid));

            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_gameName, "GET", params);

            // Check your log cat for JSON reponse
//            Log.d("All Patients: ", json.toString());

            try {
                // Checking for SUCCESS TAG

                int success=0;
                // Checking for SUCCESS TAG
                try{
                    success = json.getInt(TAG_SUCCESS);
                }catch (Exception e)
                {
                    Log.d("Service","Product Category Service not working");
                }

                if (success == 1) {
                    // products found
                    // Getting Array of patients
                    products = json.getJSONArray(TAG_GAME_NAME);

                    // looping through All Patients
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        // Storing each json item in variable
                        String id = c.getString(TAG_PID);
                        String name = c.getString(TAG_CID);
                        Log.d("Userid",id);
                        Log.d("Username",name);

                        addproductCategory(id,name);

                    }
                } else {
                    Log.d("no Product Category","found");

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
//            pDialog.dismiss();
            // updating UI from Background Thread

        }
    }


}
