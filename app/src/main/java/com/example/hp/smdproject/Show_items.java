package com.example.hp.smdproject;

import android.app.Dialog;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;

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
    LinearLayout L1;
    List<String> list1;
    String items_Descrption;
    String items_size;
    boolean btnwhishlist;
    int wishcount;
    MaterialFavoriteButton favorite;
    String item_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_items);
//        id=1;
        Helper = new DataBaseAdpter(this);
        item_id = getIntent().getStringExtra("EXTRA_SESSION_ID");
        list1 = new ArrayList<String>();
        list1 = Helper.getspeceficitem(item_id);
//        imageView=(ImageView)findViewById(R.id.img1);
//        URL url = new URL("http://image10.bizrate-images.com/resize?sq=60&uid=2216744464");
//        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//        imageView.setImageBitmap(bmp);

        items_size=list1.get(1);
        items_Descrption=list1.get(2);
        wishcount=0;
        favorite=(MaterialFavoriteButton)findViewById(R.id.wishbtn);
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

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    public void whishlistbtn()
    {
        Log.d("Check","wishbtn");
        if(btnwhishlist==false && wishcount%2==0)
        {
            Toast toast = Toast.makeText(getApplicationContext(),"WhishList_On",Toast.LENGTH_SHORT);
            toast.show();
            btnwhishlist=true;
            Helper.inserttable4("4",item_id);
        }
        if(btnwhishlist==true && wishcount%2!=0)
        {
            Toast toast = Toast.makeText(getApplicationContext(),"WhishList_OFF",Toast.LENGTH_SHORT);
            toast.show();
            btnwhishlist=false;
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
        B=(Button)findViewById(R.id.addtocart);

//        B1=(ImageButton) notifCount.findViewById(R.id.pic);
        R1=(RoundedLetterView) notifCount.findViewById(R.id.setround);

        B2=(ImageView) notifCount.findViewById(R.id.pic);
//        B3=(ImageButton) notifCount.findViewById(R.id.pic);

        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;

                Log.d("Hello","world1");
                String numberAsString2 = Integer.toString(count);
                //              tv.setText(numberAsString2);
                R1.setTitleText(numberAsString2);

            }
        });

        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                count++;

                Log.d("Hello","world4");
              //  final Dialog mDateTimeDialog = new Dialog(getApplicationContext());
                // Inflate the root layout
                final Dialog mDateTimeDialog = new Dialog(Show_items.this);

                final LinearLayout mDateTimeDialogView = (LinearLayout) getLayoutInflater().inflate(R.layout.cart_layout, null);

                android.widget.Button B=new Button(Show_items.this);
                B.setText("Hello");
//                B.setId(id);
                B.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                mDateTimeDialogView.addView(B);


                // Setup TimePicker
                // No title on the dialog window
                mDateTimeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                // Set the dialog content view
                mDateTimeDialog.setContentView(mDateTimeDialogView);


                mDateTimeDialog.show();

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
            Log.d("Hello","World");
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
            Log.d("Hello","World2");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void Button(View v)
    {
        if(v.getId()==R.id.infobtn)
        {
            Toast toast = Toast.makeText(getApplicationContext(),"Infromation",Toast.LENGTH_SHORT);
            toast.show();

            final Dialog minfoDialog = new Dialog(Show_items.this);

            final LinearLayout mDateTimeDialogView = (LinearLayout) getLayoutInflater().inflate(R.layout.cart_layout, null);

            android.widget.TextView B=new TextView(Show_items.this);
            B.setText("Hello_Descrption:_"+items_Descrption);
//                B.setId(id);
            B.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
            mDateTimeDialogView.addView(B);
            B.setTextColor(getResources().getColor(R.color.black));
            minfoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            minfoDialog.setContentView(mDateTimeDialogView);
            minfoDialog.show();

        }
    }

    public void openBottomSheet (View v) {
        Log.d("VIew ","BottomSheet");
        View view = getLayoutInflater ().inflate (R.layout.bottom_sheet, null);
//        TextView txtBackup = (TextView)view.findViewById( R.id.txt_backup);
//        TextView txtDetail = (TextView)view.findViewById( R.id.txt_detail);
//        TextView txtOpen = (TextView)view.findViewById( R.id.txt_open);
//        final TextView txtUninstall = (TextView)view.findViewById( R.id.txt_uninstall);

        L1 = (LinearLayout) view.findViewById(R.id.popup_window);
        TextView textsize=new TextView(getApplicationContext());
        textsize.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT));
        textsize.setText("Size:"+items_size);
        textsize.setCompoundDrawablePadding(15);
        textsize.setGravity(1);
        textsize.setTextColor(getResources().getColor(R.color.black));
        textsize.setPadding(15,15,15,15);
        L1.addView(textsize);

        final Dialog mBottomSheetDialog = new Dialog (Show_items.this,
                R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView (view);
        mBottomSheetDialog.setCancelable (true);
        mBottomSheetDialog.getWindow ().setLayout (LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow ().setGravity (Gravity.BOTTOM);
        mBottomSheetDialog.show ();

        textsize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Show_items.this,"Clicked Backup",Toast.LENGTH_SHORT).show();
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

}
