package com.yramnna.expenseapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rufo on 7/31/2016.
 */
public class ExpenseDbReader extends SQLiteOpenHelper {

    private static ExpenseDbReader sInstance;

    public static final int DATABASE_VERSION = 121;
    public static final String DATABASE_NAME = "expense_app.db";

    public ExpenseDbReader(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized ExpenseDbReader getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new ExpenseDbReader(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
