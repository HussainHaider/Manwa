package com.example.hp.smdproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.ArrayList;

public class userProfile extends AppCompatActivity {


    User user;
    ImageView v;
    TextView t;
    DataBaseAdpter Helper;
    ListView list;

    RelativeLayout notifCount;
    int count;
    Button B;
    ArrayList<Productdetailclass> list2;
    RoundedLetterView R1;
    ImageView B2;
    String item_id;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Helper = new DataBaseAdpter(this);

        //user=new User();
//        int f=db.delete("Users", null, null);
        //      Toast.makeText(userProfile.this, "deleted rows "+f, Toast.LENGTH_SHORT).show();


        final ImageButton imgb = (ImageButton) findViewById(R.id.user_profile_photo);
        final ImageView imgp = (ImageView) findViewById(R.id.header_cover_image);

        Intent i = getIntent();


        user = (User) i.getSerializableExtra("user");
        if (user == null) {
            Intent intent = new Intent(this, SignIn.class);
            startActivity(intent);
            finish();
            return;
        }

        String image = user.image;
        String name = user.Name;

        String str = "INFO";
        TextDrawable drawable = TextDrawable.builder().buildRound(str, R.color.blue);
        imgb.setImageDrawable(drawable);
        imgb.bringToFront();
        TextView t = (TextView) findViewById(R.id.WatchList);
        t.bringToFront();
        if (image.length() > 0)
            imgp.setImageBitmap(BitmapFactory.decodeFile(user.image));

        else {
            imgp.setImageResource(R.drawable.images);

        }
//        ArrayList<Productdetailclass> list2=Helper.getCartList("4");

        ArrayList<Productdetailclass> list2 = Helper.getWhishList("4");

        final TextView myname = (TextView) findViewById(R.id.user_profile_name);
        myname.setText(name);
        whish_custom_adapter adapter = new
                whish_custom_adapter(userProfile.this, list2,"4");
        list = (ListView) findViewById(R.id.listnumbers);
        list.setAdapter(adapter);

   /*     list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(userProfile.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

            }
        });


*/
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
//        B = (Button) findViewById(R.id.addtocart);


//        B1=(ImageButton) notifCount.findViewById(R.id.pic);
        R1 = (RoundedLetterView) notifCount.findViewById(R.id.setround);
        count = Helper.getCount("4");


        R1.setTitleText(Integer.toString(count));
        B2 = (ImageView) notifCount.findViewById(R.id.pic);
//        B3=(ImageButton) notifCount.findViewById(R.id.pic);

//        B.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                long c = Helper.inserttable11(item_id, "4");
//                if (c == -1) {
//                    Toast.makeText(getApplicationContext(), "Item Already Exist in Cart", Toast.LENGTH_SHORT)
//                            .show();
//                } else {
//                    Toast.makeText(getApplicationContext(), "Item Added to Cart Successfully", Toast.LENGTH_SHORT)
//                            .show();
//                    count++;
//                    Log.d("Hello", "world1");
//                    String numberAsString2 = Integer.toString(count);
//                    R1.setTitleText(numberAsString2);
//
//                }
//            }
//        });
        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                count++;
                list2 = Helper.getCartList("4");
                android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(userProfile.this);
                View mView = getLayoutInflater().inflate(R.layout.material_design_profile_screen_xml_ui_design, null);

                final TextView name = (TextView) mView.findViewById(R.id.user_profile_name);
                name.setText("Cart List");

                final ListView list = (ListView) mView.findViewById(R.id.listnumbers);

                cart_custom_adapter adapter = new
                        cart_custom_adapter(userProfile.this, list2,"4");

                list.setAdapter(adapter);

                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View v, int position,
                                            long arg3) {

                    }
                });
                mBuilder.setView(mView);
                final android.app.AlertDialog dialog = mBuilder.create();
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
                        Toast t = Toast.makeText(getApplicationContext(), "Order now", Toast.LENGTH_SHORT);
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

            Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            share.putExtra(Intent.EXTRA_TEXT,
                    "https://ae01.alicdn.com/kf/HTB12JGALFXXXXajXXXXq6xXFXXXZ/YuooMuoo-Fashion-Chiffon-Patchwork-font-" +
                            "b-Women-b-font-Asymmetrical-Dress-Brand-Zipper-font-b-Design.jpg");
            share.setType("text/plain");
            startActivity(share);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void buttonClick(View v) {
        if (v.getId() == R.id.user_profile_photo) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(userProfile.this);
            alertDialog.setTitle("Personal Info");
            alertDialog.setIcon(R.drawable.images);

            Context context = v.getContext();
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);

            final EditText name = new EditText(userProfile.this);
            name.setLayoutParams(lp);
            name.setHint("Name");
            name.setText(user.Name);

            layout.addView(name);


            final EditText pass = new EditText(userProfile.this);

            pass.setLayoutParams(lp);
            pass.setHint("Password");
            pass.setText(user.Password);

            layout.addView(pass);

            final EditText email = new EditText(userProfile.this);
            email.setLayoutParams(lp);
            email.setHint("Eamil");
            email.setText(user.Email);
            layout.addView(email);

            final EditText address = new EditText(userProfile.this);
            address.setLayoutParams(lp);
            address.setHint("Address");
            address.setText(user.Address);

            layout.addView(address);

            final EditText credit = new EditText(userProfile.this);
            credit.setLayoutParams(lp);
            credit.setHint("CreditCard");
            credit.setInputType(InputType.TYPE_CLASS_NUMBER);
            credit.setText(String.valueOf(user.Creidt_Card));

            layout.addView(credit);

            alertDialog.setView(layout);
            final String passw = "22";

            alertDialog.setPositiveButton("Update",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            user.Name = name.getText().toString();
                            user.Password = pass.getText().toString();
                            user.Email = email.getText().toString();
                            user.Address = address.getText().toString();
                            user.Creidt_Card = Integer.parseInt(credit.getText().toString());
                            final ImageButton imgb = (ImageButton) findViewById(R.id.user_profile_photo);

                            String name = user.Name;
                            final TextView myname = (TextView) findViewById(R.id.user_profile_name);
                            myname.setText(name);

                        }
                    });
            alertDialog.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            alertDialog.show();


        }

    }


}
