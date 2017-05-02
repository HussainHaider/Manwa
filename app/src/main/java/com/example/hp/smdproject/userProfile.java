package com.example.hp.smdproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.ArrayList;

public class userProfile extends AppCompatActivity {


    User user;
    ImageView v;
    TextView t;
    DataBaseAdpter Helper;

    ListView list;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Helper = new DataBaseAdpter(this);

        user=new User();
//        int f=db.delete("Users", null, null);
  //      Toast.makeText(userProfile.this, "deleted rows "+f, Toast.LENGTH_SHORT).show();


        final ImageButton imgb = (ImageButton) findViewById(R.id.user_profile_photo);
        final ImageView imgp = (ImageView) findViewById(R.id.header_cover_image);


        String image=user.image;
        String name=user.Name;
        String str = "INFO";
        TextDrawable drawable = TextDrawable.builder().buildRound(str, R.color.blue);
        imgb.setImageDrawable(drawable);
        imgb.bringToFront();
        TextView t=(TextView)findViewById(R.id.WatchList);
        t.bringToFront();
        if (image.length()>0)
            imgp.setImageBitmap(BitmapFactory.decodeFile(user.image));

        else
        {
            imgp.setImageResource(R.drawable.images);

        }
        ArrayList<Productdetailclass> list2=Helper.getCartList("4");

        final TextView myname = (TextView) findViewById(R.id.user_profile_name);
        myname.setText(name);
       cart_custom_adapter adapter = new
                cart_custom_adapter(userProfile.this, list2);
        list=(ListView)findViewById(R.id.listnumbers);
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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.cart:
                return true;
            case R.id.action_settings:
                Intent intent1 =new Intent(this,Categories.class);
                startActivity(intent1);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void buttonClick(View v)
    {
        if(v.getId() == R.id.user_profile_photo){
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
            credit.setText(String.valueOf( user.Creidt_Card));

            layout.addView(credit);

            alertDialog.setView(layout);
            final String passw="22";

            alertDialog.setPositiveButton("Update",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                           user.Name = name.getText().toString();
                            user.Password = pass.getText().toString();
                            user.Email = email.getText().toString();
                            user.Address =address.getText().toString();
                            user.Creidt_Card =  Integer.parseInt(credit.getText().toString());
                            final ImageButton imgb = (ImageButton) findViewById(R.id.user_profile_photo);

                            String name=user.Name;
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
