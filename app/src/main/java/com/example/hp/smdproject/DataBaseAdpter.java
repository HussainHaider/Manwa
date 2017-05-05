package com.example.hp.smdproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 13-Apr-17.
 */

public class DataBaseAdpter {
    ShopHelper Helper;
    long idcheck;

    public DataBaseAdpter(Context context) {
        Helper = new ShopHelper(context);
        idcheck=0;
        Log.d("Helper","class2");
    }

    public long inserttable2(String id,String name)
    {
        Log.d("checking","Value1");
        SQLiteDatabase db=Helper.getWritableDatabase();
        Log.d("checking","Value2");
        ContentValues value=new ContentValues();
        value.put(ShopHelper.PID,id);
        value.put(ShopHelper.PNAME,name);
        Log.d("checking","Value");
        idcheck=db.insert(ShopHelper.TABLE_NAME2,null,value);
        Log.d("checking","ID");
        return idcheck;
    }



    public List<String> gettable2(String id)
    {
        List<String> list = new ArrayList<String>();

        SQLiteDatabase db=Helper.getWritableDatabase();
        String [] columns={ShopHelper.PID,ShopHelper.PNAME};
        Log.d("checking","columns");
        String qu = "select * from "+ ShopHelper.TABLE_NAME2+" where "+ShopHelper.PID+" in (Select "+ShopHelper.ProductID+" from "+ShopHelper.TABLE_NAME7+" where "+ShopHelper.CategoryID+" = '"+id+"'"+");";
        Log.d("Query",qu);
        Cursor c = db.rawQuery(qu, null);

//        Cursor c=db.query(ShopHelper.TABLE_NAME2,columns,null,null,null,null,null,null);
        Log.d("checking","Query");
        while (c.moveToNext())
        {
            Log.d("Work","help1");
            int cid=c.getInt(0);
            String numberAsString = Integer.toString(cid);
            list.add(numberAsString);
            String name=c.getString(1);
            list.add(name);
            Log.d("Work","help2");
        }
        return list;
    }


    public long inserttable5(String id,String pid,String S,String D,String P)
    {
        SQLiteDatabase db=Helper.getWritableDatabase();
        ContentValues value=new ContentValues();
        value.put(ShopHelper.PDid,id);
        value.put(ShopHelper.ProductID,pid);
        value.put(ShopHelper.Size,S);
        value.put(ShopHelper.Price,P);
        value.put(ShopHelper.Descrption,D);
        Log.d("checking","Value");
        int a;

        idcheck=db.insert(ShopHelper.TABLE_NAME5,null,value);
              //  Log.d(idcheck);
     //   Log.d(Integer.toString(idcheck));
        return idcheck;
    }
    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
    public long inserttable5_2(String id,Bitmap bitmap)
    {
        byte[] image=null;
        Log.d("Data inserted5_2_1",id);
        if(bitmap!=null)
        {
            image=getBitmapAsByteArray(bitmap);
            Log.d("Data inserted5_2_1","Nothing");
        }




        SQLiteDatabase db=Helper.getWritableDatabase();
        ContentValues value=new ContentValues();
        value.put(ShopHelper.KEY_IMAGE2,image); //These Fields should be your String values of actual column names

        db.update(ShopHelper.TABLE_NAME5, value, ShopHelper.PDid+" ="+id, null);
        Log.d("checking","ID");
        return idcheck;
    }

    public List<String> gettable5(String id)
    {
        List<String> list = new ArrayList<String>();

        SQLiteDatabase db=Helper.getWritableDatabase();
        String [] columns={ShopHelper.ProductID,ShopHelper.PDid,ShopHelper.Size,ShopHelper.Price,ShopHelper.Descrption};
        Log.d("checking","columns");
        Cursor c=db.query(ShopHelper.TABLE_NAME5,columns,ShopHelper.ProductID+" = '"+id+"'",null,null,null,null,null);
        Log.d("checking","Query");
        while (c.moveToNext())
        {
            int cid=c.getInt(0);
            String numberAsString = Integer.toString(cid);
            list.add(numberAsString);
            cid=c.getInt(1);
            numberAsString = Integer.toString(cid);
            list.add(numberAsString);

            String Size=c.getString(2);
            list.add(Size);
            String Price=c.getString(3);
            list.add(Price);
            String Descrption=c.getString(4);
            list.add(Descrption);
        }
        return list;
    }
    public Bitmap getImage(String i){

        SQLiteDatabase db=Helper.getWritableDatabase();
        String qu = "select "+ShopHelper.KEY_IMAGE2+"  from "+ShopHelper.TABLE_NAME5+" where "+ShopHelper.PDid+"="+i+";" ;
        Log.d("Query",qu);
        Cursor cur = db.rawQuery(qu, null);

        if (cur.moveToFirst()){
            byte[] imgByte = cur.getBlob(0);
            if(imgByte==null)
            {
                return null;
            }

            cur.close();
            return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        }
        if (cur != null && !cur.isClosed()) {
            cur.close();
        }

        return null ;
    }



    public List<String> getspeceficitem(String id)
    {
        List<String> list = new ArrayList<String>();

        SQLiteDatabase db=Helper.getWritableDatabase();
        String [] columns={ShopHelper.PDid,ShopHelper.Size,ShopHelper.Descrption};
        Log.d("checking","columns");
        Cursor c=db.query(ShopHelper.TABLE_NAME5,columns,ShopHelper.PDid+" = '"+id+"'",null,null,null,null,null);
        Log.d("checking","Query");
        while (c.moveToNext())
        {
            int _id=c.getInt(0);
            String numberAsString = Integer.toString(_id);
            list.add(numberAsString);

            String Size=c.getString(1);
            list.add(Size);
            String Descrption=c.getString(2);
            list.add(Descrption);
        }
        return list;
    }
    public long inserttable4(String uid,String pid)
    {
        SQLiteDatabase db=Helper.getWritableDatabase();
        ContentValues value=new ContentValues();
        value.put(ShopHelper.pid,pid);
        value.put(ShopHelper.uid,uid);

        Log.d("checking","Value");
        idcheck=db.insert(ShopHelper.TABLE_NAME4,null,value);
        Log.d("checking","ID");
        return idcheck;
    }
    public long inserttable6(String id,String name)
    {
        Log.d("checking","Value1");
        SQLiteDatabase db=Helper.getWritableDatabase();
        Log.d("checking","Value2");
        ContentValues value=new ContentValues();
        value.put(ShopHelper.CID,id);
        value.put(ShopHelper.CNAME,name);
        Log.d("checking","Value");
        idcheck=db.insert(ShopHelper.TABLE_NAME6,null,value);
        Log.d("checking","ID");
        return idcheck;
    }
    public long inserttable7(String id1,String id2)
    {
        Log.d("checking10","Value1");
        SQLiteDatabase db=Helper.getWritableDatabase();
        Log.d("checking10","Value2");
        ContentValues value=new ContentValues();
        value.put(ShopHelper.ProductID,id1);
        value.put(ShopHelper.CategoryID,id2);
        Log.d("checking10","Value");
        idcheck=db.insert(ShopHelper.TABLE_NAME7,null,value);
        Log.d("checking","ID");
        return idcheck;
    }


    public long inserttable10(String id,String n,String D,String P)
    {
        Log.d("checking","Value1");
        SQLiteDatabase db=Helper.getWritableDatabase();
        Log.d("checking","Value2");
        ContentValues value=new ContentValues();
        value.put(ShopHelper.OfferID,id);
        value.put(ShopHelper.Name,n);
        value.put(ShopHelper.Price,P);
        value.put(ShopHelper.Descrption,D);

        Log.d("checking","Value");
        idcheck=db.insert(ShopHelper.TABLE_NAME10,null,value);
        Log.d("checking","ID");
        return idcheck;
    }
    public long inserttable10_2(String id,Bitmap B)
    {
        byte[] image=null;
        Log.d("Data inserted5_2_1",id);
        if(B!=null)
        {
            image=getBitmapAsByteArray(B);
            Log.d("Data inserted10_2_1","Nothing");
        }


        SQLiteDatabase db=Helper.getWritableDatabase();
        ContentValues value=new ContentValues();
        value.put(ShopHelper.Offer_img,image); //These Fields should be your String values of actual column names

        db.update(ShopHelper.TABLE_NAME10, value, ShopHelper.OfferID+" ="+id, null);
        Log.d("checking","ID");
        return idcheck;
    }
    public List<String> gettable10()
    {
        List<String> list = new ArrayList<String>();

        SQLiteDatabase db=Helper.getWritableDatabase();
        String [] columns={ShopHelper.OfferID,ShopHelper.Name,ShopHelper.Price,ShopHelper.Price};
        Log.d("checking","columns");
        Cursor c=db.query(ShopHelper.TABLE_NAME10,columns,null,null,null,null,null,null);
        Log.d("checking","Query");
        while (c.moveToNext())
        {
            int _id=c.getInt(0);
            String numberAsString = Integer.toString(_id);
            list.add(numberAsString);

            String Name=c.getString(1);
            list.add(Name);
            String Price=c.getString(2);
            list.add(Price);
            String Descrption=c.getString(3);
            list.add(Descrption);
        }
        return list;
    }
    public Bitmap getImage2(String i){

        SQLiteDatabase db=Helper.getWritableDatabase();
        String qu = "select "+ShopHelper.Offer_img+"  from "+ShopHelper.TABLE_NAME10+" where "+ShopHelper.OfferID+"="+i+";" ;
        Log.d("Query",qu);
        Cursor cur = db.rawQuery(qu, null);

        if (cur.moveToFirst()){
            byte[] imgByte = cur.getBlob(0);
            if(imgByte==null)
            {
                return null;
            }

            cur.close();
            return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        }
        if (cur != null && !cur.isClosed()) {
            cur.close();
        }

        return null ;
    }

    public long inserttable11(String id1,String id2)
    {
        Log.d("checking","Value1");
        SQLiteDatabase db=Helper.getWritableDatabase();
        Log.d("checking","Value2");
        ContentValues value=new ContentValues();
        value.put(ShopHelper.pid,id1);
        value.put(ShopHelper.uid,id2);
        Log.d("checking","Value");
        idcheck=db.insert(ShopHelper.TABLE_NAME11,null,value);
        Log.d("checking","ID");
        return idcheck;
    }
    public long inserttable3(String id1,String id2,String Date,String P)
    {
        Log.d("checking","Value1");
        SQLiteDatabase db=Helper.getWritableDatabase();
        Log.d("checking","Value2");
        ContentValues value=new ContentValues();
        value.put(ShopHelper.ProductID,id1);
        value.put(ShopHelper.UserID,id2);
//        value.put(ShopHelper.OrderDate,Date);
        value.put(ShopHelper.TPrice,P);
        Log.d("checking","Value");
        idcheck=db.insert(ShopHelper.TABLE_NAME3,null,value);
        Log.d("checking","ID");
        return idcheck;
    }

    public Productdetailclass getitem(String id)
    {
        Productdetailclass pr=new Productdetailclass();
        SQLiteDatabase db=Helper.getWritableDatabase();
        String query = "SELECT * FROM " + ShopHelper.TABLE_NAME5+ " where "+ShopHelper.PDid+"=?" ;

        String[] params = new String[]{ id };
        Cursor c=db.rawQuery(query,params);

        Log.d("checking","Query");
        while (c.moveToNext())
        {
            pr.PID= Integer.parseInt( c.getString(c.getColumnIndex(ShopHelper.PDid)));
            pr.Size=  Integer.parseInt(c.getString(c.getColumnIndex(ShopHelper.Size)));
            pr.Description=  c.getString(c.getColumnIndex(ShopHelper.Descrption));
            pr.Price= Integer.parseInt( c.getString(c.getColumnIndex(ShopHelper.Price)));

            byte[] imgByte =  c.getBlob(c.getColumnIndex(ShopHelper.KEY_IMAGE2));
            if(imgByte==null)
            {
                return null;
            }

            c.close();
            pr.image= BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);


        }
        return pr;
    }

    public ArrayList<Productdetailclass> getCartList(String id1)
    {
        ArrayList<Productdetailclass> list = new ArrayList<>();

        SQLiteDatabase db=Helper.getWritableDatabase();
        Log.d("checking","columns");
        String query = "SELECT * FROM " + ShopHelper.TABLE_NAME11+ " WHERE "+ShopHelper.uid+"=?" ;
        //  db.execSQL("delete from "+ ShopHelper.TABLE_NAME11);
        String[] params = new String[]{ id1 };
        Cursor c=db.rawQuery(query,params);
        Log.d("checking","Query");
        while (c.moveToNext())
        {
            list.add(getitem( c.getString(c.getColumnIndex("pid"))));

        }
        return list;
    }

    public int getCount(String id1)
    {
        ArrayList<Productdetailclass> list = new ArrayList<>();

        SQLiteDatabase db=Helper.getWritableDatabase();
        Log.d("checking","columns");
        String query = "SELECT * FROM " + ShopHelper.TABLE_NAME11+ " WHERE "+ShopHelper.uid+"=?" ;
        //  db.execSQL("delete from "+ ShopHelper.TABLE_NAME11);
        String[] params = new String[]{ id1 };
        Cursor c=db.rawQuery(query,params);
        Log.d("checking","Query");

        return c.getCount();
    }




    public long removeFromCart(String pid,String uid)
    {
        long check=0;
        SQLiteDatabase db=Helper.getWritableDatabase();
        Log.d("checking","columns");
        //  String query = "DELETE * FROM " + ShopHelper.TABLE_NAME11+ " WHERE "+ShopHelper.pid+"=?" ;
        //  db.execSQL("delete from "+ ShopHelper.TABLE_NAME11);
        check=db.delete(ShopHelper.TABLE_NAME11, ShopHelper.pid + "=" + pid+ " AND "+ ShopHelper.uid+"="+uid, null);
        //   String[] params = new String[]{ id1 };
        //  db.execSQL(query,params);
        Log.d("checking","Query");

        return check;
    }

    public long removeFromWhish(String pid,String uid)
    {
        long check=0;
        SQLiteDatabase db=Helper.getWritableDatabase();
        Log.d("checking","columns");
        //  String query = "DELETE * FROM " + ShopHelper.TABLE_NAME11+ " WHERE "+ShopHelper.pid+"=?" ;
        //  db.execSQL("delete from "+ ShopHelper.TABLE_NAME11);
        check=db.delete(ShopHelper.TABLE_NAME4, ShopHelper.pid + "=" + pid+ " AND "+ ShopHelper.uid+"="+uid, null);
        //   String[] params = new String[]{ id1 };
        //  db.execSQL(query,params);
        Log.d("checking","Query");

        return check;
    }
    public ArrayList<Productdetailclass> getWhishList(String id1)
    {
        ArrayList<Productdetailclass> list = new ArrayList<>();

        SQLiteDatabase db=Helper.getWritableDatabase();
        Log.d("checking","columns");
        String query = "SELECT * FROM " + ShopHelper.TABLE_NAME4+ " WHERE "+ShopHelper.uid+"=?" ;
        //  db.execSQL("delete from "+ ShopHelper.TABLE_NAME11);
        String[] params = new String[]{ id1 };
        Cursor c=db.rawQuery(query,params);
        Log.d("checking","Query");
        while (c.moveToNext())
        {
            list.add(getitem( c.getString(c.getColumnIndex("pid"))));

        }
        return list;
    }

     public  long deleteCart(String uid)
     {
         long check=0;
         SQLiteDatabase db=Helper.getWritableDatabase();
         Log.d("checking","columns");
         //  String query = "DELETE * FROM " + ShopHelper.TABLE_NAME11+ " WHERE "+ShopHelper.pid+"=?" ;
         //  db.execSQL("delete from "+ ShopHelper.TABLE_NAME11);
         check=db.delete(ShopHelper.TABLE_NAME11, ShopHelper.uid + "=" + uid, null);
         //   String[] params = new String[]{ id1 };
         //  db.execSQL(query,params);
         Log.d("checking","Query");

         return check;

     }

    static class ShopHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "SHOPDB";

        private static final String TABLE_NAME1 = "Users";
        private static final String TABLE_NAME2 = "Products";
        private static final String TABLE_NAME3 = "Orders";
        private static final String TABLE_NAME4 = "Wishlist";
        private static final String TABLE_NAME5 = "ProductDetail";
        private static final String TABLE_NAME6 = "Category";
        private static final String TABLE_NAME7 = "ProductCategory";
        private static final String TABLE_NAME8 = "Gold_User";
        private static final String TABLE_NAME9 = "Premium_User";
        private static final String TABLE_NAME10 = "SpecialOffer";
        private static final String TABLE_NAME11 = "Cart";
        private static final int DATABASE_VERSION = 16;
        //Table 1 attribute
        private static final String KEY_IMAGE1 = "Photo";
        private static final String UID = "_id";
        private static final String NAME = "name";
        private static final String EMAIL = "email";
        private static final String ADDRESS = "address";
        private static final String COUNTRY = "country";
        private static final String Credit = "CCard";
        private static final String PASSWORD = "password";

        //Table 2 attribute
        private static final String PID = "ID";
        private static final String PNAME = "Name";
        private static final String NO_OF_PRODUCT = "product_no";
        //Table 3 attribute
        private static final String OID = "_ID";
        private static final String OrderDate = "Date";
        private static final String ProductID = "PID"; //Universal
        private static final String UserID = "UID";   //Universal
        private static final String TPrice = "Tprice";
        //Table 5 attribute
        private static final String PDid = "id_";
        private static final String KEY_IMAGE2 = "image_data";
        private static final String Price = "price";
        private static final String Descrption = "description";
        private static final String Size = "size";
        //Table 6 attribute
        private static final String CID = "_Id";
        private static final String CNAME = "cname";
        //Table 7 attribute
        private static final String CategoryID = "CID"; //Universal
        //Table 8 attribute
        private static final String Birthdate = "Bdate";
        //Table 9 attribute
        private static final String Phonenumber = "Pnumber";
        //Table 10 attribute
        private static final String OfferID = "OfferID";
        private static final String Name = "Name";
        private static final String Offer_img = "img";
        //Table 11 attribute
        private static final String uid = "uid";
        private static final String pid = "pid";


        public ShopHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            Log.d("checking", "Present");


        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d("checking", "Creating");
            String query1 = "CREATE TABLE " + TABLE_NAME1 + " ( " + UID + "" +
                    " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + NAME + " VARCHAR(255) ," + KEY_IMAGE1 + " BLOB ," +
                    EMAIL + " VARCHAR(255) ," + ADDRESS + " VARCHAR(255) ," + COUNTRY + " VARCHAR(255) ," + Credit + " INTEGER ," + PASSWORD + " VARCHAR(255) );";
            Log.d("checking", query1);
            try {
                db.execSQL(query1);
                Log.d("check for something", "getting done with query1");
            } catch (SQLException e) {
                Log.d("check for something", "getting error1");
            }
            //---------------------------------------------------------------------------------------------------------------

            String query2 = "CREATE TABLE " + TABLE_NAME2 + " ( " + PID + "" +
                    " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + PNAME + " VARCHAR(255) ," + NO_OF_PRODUCT + " INTEGER );";
            Log.d("checking", query2);
            try {
                db.execSQL(query2);
                Log.d("check for something", "getting done with query2");
            } catch (SQLException e) {
                Log.d("check for something", "getting error2");
            }
            //---------------------------------------------------------------------------------------------------------------


            String query3 = "CREATE TABLE " + TABLE_NAME3 + " ( " + OID + "" +
                    " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + ProductID + " INTEGER ," + UserID +
                    " INTEGER, " + OrderDate + " datetime ," + TPrice + " INTEGER , FOREIGN KEY (" + UserID + ") REFERENCES " +
                    TABLE_NAME1 + "(" + UID + ") , FOREIGN KEY (" + ProductID + ") REFERENCES " + TABLE_NAME2 + "(" + PID + "));";
            Log.d("checking", query3);
            try {
                db.execSQL(query3);
                Log.d("check for something", "getting done with query3");
            } catch (SQLException e) {
                Log.d("check for something", "getting error3");
            }
            //---------------------------------------------------------------------------------------------------------------


            String query4 = "CREATE TABLE " + TABLE_NAME4 + " ( " + pid + " INTEGER ," + uid +
                    " INTEGER , FOREIGN KEY (" + uid + ") REFERENCES " +
                    TABLE_NAME1 + "(" + UID + ") , FOREIGN KEY (" + pid + ") REFERENCES " + TABLE_NAME2 + "(" + PID + "), PRIMARY KEY ("+pid+","+uid+"));";
            Log.d("checking", query4);
            try {
                db.execSQL(query4);
                Log.d("check for something", "getting done with query11");
            } catch (SQLException e) {
                Log.d("check for something", "getting error11");
            }
            //---------------------------------------------------------------------------------------------------------------

            String query5 = "CREATE TABLE " + TABLE_NAME5 + " ( " + PDid + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," +
                    ProductID + " INTEGER," + KEY_IMAGE2 + " BLOB, " +
                    Price + " INTEGER ," + Descrption + " TEXT ," + Size + " INTEGER , FOREIGN KEY (" + ProductID + ") REFERENCES " + TABLE_NAME2 + "(" + PID + "));";
            Log.d("checking", query5);
            try {
                db.execSQL(query5);
                Log.d("check for something", "getting done with query5");
            } catch (SQLException e) {
                Log.d("check for something", "getting error5");
            }

            //---------------------------------------------------------------------------------------------------------------


            String query6 = "CREATE TABLE " + TABLE_NAME6 + " ( " + CID + "" +
                    " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + CNAME + " VARCHAR(255));";
            Log.d("checking", query6);
            try {
                db.execSQL(query6);
                Log.d("check for something", "getting done with query6");
            } catch (SQLException e) {
                Log.d("check for something", "getting error6");
            }
            //---------------------------------------------------------------------------------------------------------------

            String query7 = "CREATE TABLE " + TABLE_NAME7 + " ( " + ProductID + " INTEGER ," + CategoryID +
                    " INTEGER, FOREIGN KEY (" + CategoryID + ") REFERENCES " +
                    TABLE_NAME6 + "(" + CID + ") , FOREIGN KEY (" + ProductID + ") REFERENCES " + TABLE_NAME2 + "(" + PID + "));";
            Log.d("checking", query7);
            try {
                db.execSQL(query7);
                Log.d("check for something", "getting done with query7");
            } catch (SQLException e) {
                Log.d("check for something", "getting error7");
            }
            //---------------------------------------------------------------------------------------------------------------
            String query8 = "CREATE TABLE " + TABLE_NAME8 + " ( " + UserID +
                    " INTEGER," + Birthdate + " datetime , FOREIGN KEY (" + UserID + ") REFERENCES " +
                    TABLE_NAME1 + "(" + UID + "));";
            Log.d("checking", query8);
            try {
                db.execSQL(query8);
                Log.d("check for something", "getting done with query8");
            } catch (SQLException e) {
                Log.d("check for something", "getting error8");
            }
            //---------------------------------------------------------------------------------------------------------------
            String query9 = "CREATE TABLE " + TABLE_NAME9 + " ( " + UserID +
                    " INTEGER, " + Phonenumber + " INTEGER , FOREIGN KEY (" + UserID + ") REFERENCES " +
                    TABLE_NAME1 + "(" + UID + "));";
            Log.d("checking", query9);
            try {
                db.execSQL(query9);
                Log.d("check for something", "getting done with query9");
            } catch (SQLException e) {
                Log.d("check for something", "getting error9");
            }
            //---------------------------------------------------------------------------------------------------------------

            String query10 = "CREATE TABLE " + TABLE_NAME10 + " ( " + OfferID +
                    " INTEGER ," +Name+" TEXT ,"+Price+" INTEGER ,"+Descrption+" TEXT ,"+Offer_img+" BLOB );";
            Log.d("checking", query10);
            try {
                db.execSQL(query10);
                Log.d("check for something", "getting done with query10");
            } catch (SQLException e) {
                Log.d("check for something", "getting error10");
            }
            //---------------------------------------------------------------------------------------------------------------
            String query11 = "CREATE TABLE " + TABLE_NAME11 + " ( " + pid + " INTEGER ," + uid +
                    " INTEGER , FOREIGN KEY (" + uid + ") REFERENCES " +
                    TABLE_NAME1 + "(" + UID + ") , FOREIGN KEY (" + pid + ") REFERENCES " + TABLE_NAME2 + "(" + PID + "), PRIMARY KEY ("+pid+","+uid+"));";
            Log.d("checking", query11);
            try {
                db.execSQL(query11);
                Log.d("check for something", "getting done with query11");
            } catch (SQLException e) {
                Log.d("check for something", "getting error11");
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            String query11 = "DROP TABLE IF EXISTS " + TABLE_NAME1;
            try {
                db.execSQL(query11);
                Log.d("check for something", "getting done with query11");
                //onCreate(db);
            } catch (SQLException e) {
                Log.d("check for something", "getting error with query11");

            }
            //---------------------------------------------------------------------------------------------------------------

            String query12 = "DROP TABLE IF EXISTS " + TABLE_NAME2;
            try {
                db.execSQL(query12);
                Log.d("check for something", "getting done with query12");
                //onCreate(db);
            } catch (SQLException e) {
                Log.d("check for something", "getting error with query12");

            }
            //---------------------------------------------------------------------------------------------------------------

            String query13 = "DROP TABLE IF EXISTS " + TABLE_NAME3;
            try {
                db.execSQL(query13);
                Log.d("check for something", "getting done with query13");
                //onCreate(db);
            } catch (SQLException e) {
                Log.d("check for something", "getting error with query13");
            }
            //---------------------------------------------------------------------------------------------------------------

            String query14 = "DROP TABLE IF EXISTS " + TABLE_NAME4;
            try {
                db.execSQL(query14);
                Log.d("check for something", "getting done with query14");
                //onCreate(db);
            } catch (SQLException e) {
                Log.d("check for something", "getting error with query14");
            }
            //---------------------------------------------------------------------------------------------------------------

            String query15 = "DROP TABLE IF EXISTS " + TABLE_NAME5;
            try {
                db.execSQL(query15);
                Log.d("check for something", "getting done with query15");
                //onCreate(db);
            } catch (SQLException e) {
                Log.d("check for something", "getting error with query15");
            }
            //---------------------------------------------------------------------------------------------------------------


            String query16 = "DROP TABLE IF EXISTS " + TABLE_NAME6;
            try {
                db.execSQL(query16);
                Log.d("check for something", "getting done with query16");
                //onCreate(db);
            } catch (SQLException e) {
                Log.d("check for something", "getting error with query16");
            }
            //---------------------------------------------------------------------------------------------------------------


            String query17 = "DROP TABLE IF EXISTS " + TABLE_NAME7;
            try {
                db.execSQL(query17);
                Log.d("check for something", "getting done with query17");
                //onCreate(db);
            } catch (SQLException e) {
                Log.d("check for something", "getting error with query17");
            }
            //---------------------------------------------------------------------------------------------------------------

            String query18 = "DROP TABLE IF EXISTS " + TABLE_NAME8;
            try {
                db.execSQL(query18);
                Log.d("check for something", "getting done with query18");
                //onCreate(db);
            } catch (SQLException e) {
                Log.d("check for something", "getting error with query18");
            }
            //---------------------------------------------------------------------------------------------------------------
            String query19 = "DROP TABLE IF EXISTS " + TABLE_NAME9;
            try {
                db.execSQL(query19);
                Log.d("check for something", "getting done with query19");
                //onCreate(db);
            } catch (SQLException e) {
                Log.d("check for something", "getting error with query19");
            }
            //---------------------------------------------------------------------------------------------------------------
            String query20 = "DROP TABLE IF EXISTS " + TABLE_NAME10;
            try {
                db.execSQL(query20);
                Log.d("check for something", "getting done with query20");
               // onCreate(db);
            } catch (SQLException e) {
                Log.d("check for something", "getting error with query20");
            }
            //---------------------------------------------------------------------------------------------------------------
            String query21 = "DROP TABLE IF EXISTS " + TABLE_NAME11;
            try {
                db.execSQL(query21);
                Log.d("check for something", "getting done with query21");
                  onCreate(db);
            } catch (SQLException e) {
                Log.d("check for something", "getting error with query21");
            }

            Log.d("check for updation", "yes it is obtain");


            Log.d("check for updation", "yes it is obtain");


        }
    }
}