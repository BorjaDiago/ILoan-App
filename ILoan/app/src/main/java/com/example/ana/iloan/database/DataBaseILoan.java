package com.example.ana.iloan.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseILoan extends SQLiteOpenHelper {

    public DataBaseILoan(Context context) {
        super(context, DataBaseSettings.DATABASE_NAME, null, DataBaseSettings.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DataBaseSettings.FRIEND_TABLE_CREATE);
        sqLiteDatabase.execSQL(DataBaseSettings.ITEM_CREATE_TABLE);
        sqLiteDatabase.execSQL(DataBaseSettings.LOAN_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS friend");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS item");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS loan");
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if(!db.isReadOnly()){
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
}
