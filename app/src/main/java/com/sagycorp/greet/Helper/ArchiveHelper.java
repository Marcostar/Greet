package com.sagycorp.greet.Helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Dzeko on 1/22/2016.
 */
public class ArchiveHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "greet.sqlite";
    private static final int DATABASE_VERSION = 1;

    public ArchiveHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Get a single fact
    public Cursor getFact(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select * from fact where _id = "+id+"", null);
        return result;
    }

    public Cursor getQuote(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select * from quotes where _id = "+id+"",null);
        return result;
    }
}
