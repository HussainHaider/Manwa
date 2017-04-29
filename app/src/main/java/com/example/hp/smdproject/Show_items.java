package com.example.hp.smdproject;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;

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
    RoundedLetterView R1;
    ImageView B2;
    ImageView imageView;
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

//        imageView=(ImageView)findViewById(R.id.img1);
//        URL url = new URL("http://image10.bizrate-images.com/resize?sq=60&uid=2216744464");
//        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//        imageView.setImageBitmap(bmp);

        items_size=list1.get(1);
//        items_size = "";
        items_Descrption=list1.get(2);
//        items_Descrption = "";

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



/*        T=(TextView)findViewById(R.id.size);
        dropdown = (Spinner)findViewById(R.id.spin);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
                // On selecting a spinner item
                //String Text  = adapter.getItemAtPosition(position).toString();
                String Text = dropdown.getSelectedItem().toString();
                Log.d("String is:",Text);
                if(Text.matches("") || Text.matches("Size")){
                    //conditions accordingly
                    return;
                }
                else
                    T.setText(Text);

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                T.setText("Size");
            }
        });*/
        new LoadAllProductName().execute();


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

        B2 = (ImageView) notifCount.findViewById(R.id.pic);
//        B3=(ImageButton) notifCount.findViewById(R.id.pic);

        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;

                Log.d("Hello", "world1");
                String numberAsString2 = Integer.toString(count);
                //              tv.setText(numberAsString2);
                R1.setTitleText(numberAsString2);

            }
        });

        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                count++;

                Log.d("Hello", "world4");
                //  final Dialog mDateTimeDialog = new Dialog(getApplicationContext());
                // Inflate the root layout
                final Dialog CartDialog = new Dialog(Show_items.this);


                CartDialog.setTitle("Cart Products");


                final LinearLayout CartDialogView = (LinearLayout) getLayoutInflater().inflate(R.layout.cart_layout, null);

                android.widget.Button B = new Button(Show_items.this);
                B.setText("Hello");
                B.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                CartDialogView.addView(B);

                ScrollView S = new ScrollView(Show_items.this);
                S.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                //Testing
                for (int i = 0; i < 25; i++) {


                    LinearLayout L = new LinearLayout(Show_items.this);
                    L.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    L.setOrientation(LinearLayout.HORIZONTAL);


                    TextView T = new TextView(Show_items.this);
                    T.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    T.setText("Jeans");
                    T.setTextSize(25);
                    L.addView(T);

                    Button B1 = new Button(Show_items.this);
                    B1.setText("X");
                    B1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    B1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast toast = Toast.makeText(getApplicationContext(), "clicking" + v.getId(), Toast.LENGTH_LONG);
                            toast.show();
                            D = (LinearLayout) v.getParent();
                            CartDialogView.removeView(D);

                        }
                    });
                    L.addView(B1);
                    CartDialogView.addView(L);

                }
                S.addView(CartDialogView);


                CartDialog.requestWindowFeature(Window.FEATURE_SWIPE_TO_DISMISS);
                CartDialog.setContentView(S);

                CartDialog.show();
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
//            final Dialog mDateTimeDialog = new Dialog(this);
//            // Inflate the root layout
//
//            final LinearLayout mDateTimeDialogView = (LinearLayout) getLayoutInflater().inflate(R.layout.cart_layout, null);
//
//            android.widget.Button B=new Button(getApplicationContext());
//            B.setText("Hello");
//            B.setId(id);
//            B.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
//            mDateTimeDialogView.addView(B);
//
//
//            ((Button) mDateTimeDialogView.findViewById(R.id.SetDateTime)).setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    Log.d("Start your","work1");
//                    mDateTimeDialog.dismiss();
//                }
//            });
//
//
//            ((Button) mDateTimeDialogView.findViewById(R.id.CancelDialog)).setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    // TODO Auto-generated method stub
//                    Log.d("Start your","work2");
//                    mDateTimeDialog.cancel();
//                }
//            });
//
//            // Reset Date and Time pickers when the "Reset" button is clicked
//
//            ((Button) mDateTimeDialogView.findViewById(R.id.ResetDateTime)).setOnClickListener(new View.OnClickListener() {
//
//                public void onClick(View v) {
//                    // TODO Auto-generated method stub
//                    Log.d("Start your","work3");
//                }
//            });
//
//            // Setup TimePicker
//            // No title on the dialog window
//            mDateTimeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            // Set the dialog content view
//            mDateTimeDialog.setContentView(mDateTimeDialogView);
//
//
//            mDateTimeDialog.show();
//

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

            android.widget.TextView B = new TextView(Show_items.this);
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
//        txtDetail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(Show_items.this,"Clicked Detail",Toast.LENGTH_SHORT).show();
//                mBottomSheetDialog.dismiss();
//            }
//        });
//        txtOpen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(Show_items.this,"Clicked Open",Toast.LENGTH_SHORT).show();
//                mBottomSheetDialog.dismiss();
//            }
//        });
//        txtUninstall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(Show_items.this,"Clicked Uninstall",Toast.LENGTH_SHORT).show();
//                mBottomSheetDialog.dismiss();
//            }
//        });

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
