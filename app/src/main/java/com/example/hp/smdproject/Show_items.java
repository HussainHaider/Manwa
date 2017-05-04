package com.example.hp.smdproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Show_items extends AppCompatActivity {

    String[] items = new String[]{"2.3", "5.6", "8.8"};
    Spinner dropdown;
    TextView T;
    static Dialog D1;
    int count;
    Button B;
    ArrayList<Productdetailclass> list2;
    RoundedLetterView R1;
    ImageView B2;
    ImageView img;
    ImageButton B3;
    RelativeLayout notifCount;
    DataBaseAdpter Helper;
    LinearLayout L1, D;
    List<String> list1;
    String items_Descrption;
    String items_size;
    boolean btnwhishlist;
    int wishcount;
    MaterialFavoriteButton favorite;
    String item_id;
    String MY_URL_STRING;


    JSONArray products = null;
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    private static String url_all_gameName = "https://stopshop321.000webhostapp.com/getimage2.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_GAME_NAME = "image_detail";
    private static final String TAG_IMAGE = "image";

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_items);
//        id=1;
        Helper = new DataBaseAdpter(this);
        D = new LinearLayout(this);
        item_id = getIntent().getStringExtra("EXTRA_SESSION_ID");
        list1 = new ArrayList<String>();
        list1 = Helper.getspeceficitem(item_id);

        MY_URL_STRING = "http://www.cricketact.com.au/images/junior-competition.jpg";


        mAdView = (AdView) findViewById(R.id.adView1);
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


//        imageView=(ImageView)findViewById(R.id.img1);
//        URL url = new URL("http://image10.bizrate-images.com/resize?sq=60&uid=2216744464");
//        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//        imageView.setImageBitmap(bmp);

        items_size = list1.get(1);
//        items_size = "";
        items_Descrption = list1.get(2);
//        items_Descrption = "";

        img = (ImageView) findViewById(R.id.img1);
        img.setImageBitmap(Helper.getImage(item_id));
        wishcount = 0;
        favorite = (MaterialFavoriteButton) findViewById(R.id.wishbtn);
        favorite.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                        //
                        whishlistbtn();
                    }
                });

    }

    public void callimagetask(String a) {
        new DownloadImageTask((ImageView) findViewById(R.id.img1))
                .execute(a);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    public void whishlistbtn() {
        Log.d("Check", "wishbtn");
        if (btnwhishlist == false && wishcount % 2 == 0) {
            Toast toast = Toast.makeText(getApplicationContext(), "WhishList_On", Toast.LENGTH_SHORT);
            toast.show();
            btnwhishlist = true;
            Helper.inserttable4("4", item_id);
        }
        if (btnwhishlist == true && wishcount % 2 != 0) {
            Toast toast = Toast.makeText(getApplicationContext(), "WhishList_OFF", Toast.LENGTH_SHORT);
            toast.show();
            btnwhishlist = false;
        }
        wishcount++;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.items_menu, menu);
        MenuItem item = menu.findItem(R.id.action_add);
        MenuItemCompat.setActionView(item, R.layout.badge_count);
        notifCount = (RelativeLayout) MenuItemCompat.getActionView(item);
//        tv = (TextView) notifCount.findViewById(R.id.actionbar_notifcation_textview);
//        tv.setText("0");
        B = (Button) findViewById(R.id.addtocart);


//        B1=(ImageButton) notifCount.findViewById(R.id.pic);
        R1 = (RoundedLetterView) notifCount.findViewById(R.id.setround);
        count = Helper.getCount("4");


        R1.setTitleText(Integer.toString(count));
        B2 = (ImageView) notifCount.findViewById(R.id.pic);
//        B3=(ImageButton) notifCount.findViewById(R.id.pic);

        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long c = Helper.inserttable11(item_id, "4");
                if (c == -1) {
                    Toast.makeText(getApplicationContext(), "Item Already Exist in Cart", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Toast.makeText(getApplicationContext(), "Item Added to Cart Successfully", Toast.LENGTH_SHORT)
                            .show();
                    count++;
                    Log.d("Hello", "world1");
                    String numberAsString2 = Integer.toString(count);
                    R1.setTitleText(numberAsString2);

                }
            }
        });
        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                count++;
                list2 = Helper.getCartList("4");
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Show_items.this);
                View mView = getLayoutInflater().inflate(R.layout.material_design_profile_screen_xml_ui_design, null);

                final TextView name = (TextView) mView.findViewById(R.id.user_profile_name);
                name.setText("Cart List");

                final ListView list = (ListView) mView.findViewById(R.id.listnumbers);

                cart_custom_adapter adapter = new
                        cart_custom_adapter(Show_items.this, list2);

                list.setAdapter(adapter);

                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View v, int position,
                                            long arg3) {

                    }
                });
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        // TODO Auto-generated method stub
                        count = Helper.getCount("4");
                        R1.setTitleText(Integer.toString(count));
                    }
                });
                final Button Btn = (Button) mView.findViewById(R.id.button_order);

                Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast t=Toast.makeText(getApplicationContext(),"Order now",Toast.LENGTH_SHORT);
                        t.show();


//                        for(int i=0;i<list2.size();i++)
//                        {
//                            Helper.inserttable3(Integer.toString(list2.get(i).ID),"4","4/5/2017",Integer.toString(list2.get(i).Price));
//                            Helper.removeFromCart(Integer.toString(list2.get(i).PID));
//                        }
//                        list2.clear();
//
//
//
//                        count = Helper.getCount("4");
//                        R1.setTitleText(Integer.toString(count));


                    }
                });

                dialog.show();
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            Log.d("Menu", "World1");

            return true;
        }
        if (id == R.id.action_share) {
            Log.d("Menu", "World2");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Button(View v) {
        if (v.getId() == R.id.infobtn) {
            Toast toast = Toast.makeText(getApplicationContext(), "Infromation", Toast.LENGTH_SHORT);
            toast.show();

            final Dialog minfoDialog = new Dialog(Show_items.this);

            final LinearLayout mDateTimeDialogView = (LinearLayout) getLayoutInflater().inflate(R.layout.cart_layout, null);

            TextView B = new TextView(Show_items.this);
            B.setText("Hello_Descrption:_" + items_Descrption);
//                B.setId(id);
            B.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            mDateTimeDialogView.addView(B);
            B.setTextColor(getResources().getColor(R.color.black));
            minfoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            minfoDialog.setContentView(mDateTimeDialogView);
            minfoDialog.show();


        }
    }

    public void openBottomSheet(View v) {
        Log.d("VIew ", "BottomSheet");
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
//        TextView txtBackup = (TextView)view.findViewById( R.id.txt_backup);
//        TextView txtDetail = (TextView)view.findViewById( R.id.txt_detail);
//        TextView txtOpen = (TextView)view.findViewById( R.id.txt_open);
//        final TextView txtUninstall = (TextView)view.findViewById( R.id.txt_uninstall);

        L1 = (LinearLayout) view.findViewById(R.id.popup_window);
        TextView textsize = new TextView(getApplicationContext());
        textsize.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
        textsize.setText("Size:" + items_size);
        textsize.setCompoundDrawablePadding(15);
        textsize.setGravity(1);
        textsize.setTextColor(getResources().getColor(R.color.black));
        textsize.setPadding(15, 15, 15, 15);
        L1.addView(textsize);

        final Dialog mBottomSheetDialog = new Dialog(Show_items.this,
                R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();

        textsize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Show_items.this, "Clicked Backup", Toast.LENGTH_SHORT).show();
                mBottomSheetDialog.dismiss();
            }
        });


    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            Toast toast = Toast.makeText(getApplicationContext(), "Getting Image", Toast.LENGTH_LONG);
            toast.show();
            bmImage.setImageBitmap(result);
        }
    }

    class LoadAllProductName extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Show_items.this);
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
            params.add(new BasicNameValuePair("image_id", "1"));

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
                        String image = c.getString(TAG_IMAGE);
                        Log.d("IMAGE_URL", image);
//                        Toast toast = Toast.makeText(getApplicationContext(),"Image URL"+image,Toast.LENGTH_LONG);
//                        toast.show();
                        callimagetask(image);

                    }
                } else {
                    Log.d("no Product", "found");
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
