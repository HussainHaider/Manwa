package com.example.hp.smdproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;

public class userProfile extends AppCompatActivity {

    DataBaseAdpter dbHelper;
    SQLiteDatabase db ;

    User user;
    ImageView v;
    TextView t;

    ListView list;
    String[] web = {
            "jeans",
            "Pents",
            "shirts",
            "suits",
            "suits",
            "accessories",
            "pents"
    } ;
    Integer[] imageId = {
            R.mipmap.pents,
            R.mipmap.shirt,
            R.mipmap.suit,
            R.mipmap.jeans,
            R.mipmap.shirt,
            R.mipmap.accessories,
            R.mipmap.pents

    };

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        dbHelper=new DataBaseAdpter(this);


        user=new User();
        int f=db.delete("Users", null, null);
        Toast.makeText(userProfile.this, "deleted rows "+f, Toast.LENGTH_SHORT).show();




        final ImageButton imgb = (ImageButton) findViewById(R.id.user_profile_photo);
        final ImageView imgp = (ImageView) findViewById(R.id.header_cover_image);


//        setSupportActionBar(myToolbar);
//        cart_custom_adapter adapter = new
//                cart_custom_adapter(userProfile.this, web, imageId);
//        list=(ListView)findViewById(R.id.listnumbers);
//        list.setAdapter(adapter);
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                Toast.makeText(userProfile.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
//
//            }
//        });

        String query = "SELECT * FROM table2";
        String query1 = "SELECT * FROM Users WHERE name=?";
        String query2 = "SELECT * FROM table5 WHERE name=?";
        String[] params = new String[]{ "Shoib Khalid"};
        Cursor dataStore = db.rawQuery(query1,params);

        while(dataStore.moveToNext()) {

            user.ID=(dataStore.getInt(dataStore.getColumnIndex("_id")));
            user.Name=(dataStore.getString(dataStore.getColumnIndex("name")));
            user.image=(dataStore.getString(dataStore.getColumnIndex("Photo")));
            user.Country=(dataStore.getString(dataStore.getColumnIndex("country")));
            user.Address=(dataStore.getString(dataStore.getColumnIndex("address")));
            user.Password=(dataStore.getString(dataStore.getColumnIndex("password")));
            user.Email=(dataStore.getString(dataStore.getColumnIndex("email")));
            user.Creidt_Card=(dataStore.getInt(dataStore.getColumnIndex("CCard")));
            Toast.makeText(userProfile.this, "flag "+user.Name, Toast.LENGTH_SHORT).show();


            //  Toast.makeText(MainActivity.this, dataStore.getString(dataStore.getColumnIndex("image")), Toast.LENGTH_SHORT).show();

        //    String[] params = new String[]{ c.getName() };

          //  Cursor ccPhone = db.rawQuery(query2,params);
         //   Cursor ccEmail = db.rawQuery(query1,params);
          /*  ArrayList<String> m=new ArrayList<String>();
            ArrayList<String> p=new ArrayList<String>();


            while(ccPhone.moveToNext()) {

                p.add(ccPhone.getString(ccPhone.getColumnIndex("num")));
            }
            c.setPhone(p);

            while(ccEmail.moveToNext()) {

                m.add(ccEmail.getString(ccEmail.getColumnIndex("email")));
            }

            c.setEmails(m);
            Contact_L.add(c);*/
        }

        String image=user.image;
        String name=user.Name;
        char a = name.charAt(0);
        char b = '\0';
        int ss = user.getsur();
        if (ss != -1)
            b =user.Name.charAt(ss);
        String str = "" + a + "" + b + "";
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
        final TextView myname = (TextView) findViewById(R.id.user_profile_name);
        myname.setText(name);


        //final ListView l = (ListView) findViewById(R.id.listnumbers);
        //ArrayList<String> list_p = filtered.get(position).getPhone();
        //char a=filteredNotes.get(position).getName().charAt(0);
        //int i=filteredNotes.get(position).getsur();
        //char b;
        //if(i==-1) {
         //   b = ' ';
        //}
        //else {
          //  b = filteredNotes.get(position).getName().charAt(i);
        //}
        //String str=""+a+""+b+"";
        //ccv.setTitleText(str);
        //ccv.invalidate();

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
                            char a = name.charAt(0);
                            char b = '\0';
                            int ss = user.getsur();
                            if (ss != -1)
                                b =user.Name.charAt(ss);
                            String str = "" + a + "" + b + "";
                            TextDrawable drawable = TextDrawable.builder().buildRound(str, R.color.blue);

                            imgb.setImageDrawable(drawable);

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
