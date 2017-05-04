package com.example.hp.smdproject;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by user on 11/2/2016.
 */
public class DetailContentProvider extends ContentProvider {

    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    static
    {
        matcher.addURI("com.example.hp.smdproject.DetailContentProvider","ProductDetail",1);

    }

    private DataBaseAdpter.ShopHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        dbHelper = new DataBaseAdpter.ShopHelper(getContext());
        db = dbHelper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        if(matcher.match(uri)==1)
        {
            if(selection==null) {
                return db.query("ProductDetail", projection, null, null, null, null, null);
            }
            else
            {
                return db.query("ProductDetail", projection, selection, selectionArgs, null, null, null);
            }
        }

        return null;
    }
    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        if(matcher.match(uri)==1)
        {

            db.insert("ProductDetail",null,values);
            Toast.makeText(getContext(), "inserted", Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
