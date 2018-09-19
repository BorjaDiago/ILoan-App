package com.example.ana.iloan.database;


public class DataBaseSettings {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "iloan.db";

    //    FRIEND database table
    private static final String TABLE_NAME_FRIEND = "friend";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SURNAME = "surname";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_IMAGE = "friend_image";
    public static final String FRIEND_TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME_FRIEND + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_SURNAME + " TEXT, " +
                    COLUMN_PHONE + " TEXT, " +
                    COLUMN_IMAGE + " TEXT);";

    //    ITEM database table
    private static final String TABLE_NAME_ITEM = "item";
    private static final String COLUMN_ITEM_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_KIND = "kind";
    private static final String COLUMN_GENRE = "genre";
    private static final String COLUMN_ITEM_IMAGE = "item_image";
    private static final String COLUMN_YEAR = "year";
    public static final String ITEM_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME_ITEM + " ( " +
                    COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_KIND + " TEXT, " +
                    COLUMN_GENRE + " TEXT, " +
                    COLUMN_ITEM_IMAGE + " TEXT, " +
                    COLUMN_YEAR + " TEXT);";

    //    LOAN database table
    private static final String TABLE_NAME_LOAN = "loan";
    private static final String COLUMN_LOAN_ID = "_id";
    private static final String COLUMN_FOREIGN_FRIEND_ID = "id_friend";
    private static final String COLUMN_FOREIGN_ITEM_ID = "id_item";
    private static final String COLUMN_INDATE = "in_date";
    private static final String COLUMN_OUTDATE = "out_date";
    private static final String COLUMN_COMENTS = "coments";
    private static final String COLUMN_LOAN_IMAGE = "loan_image";
    public static final String LOAN_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME_LOAN + " ( " +
                    COLUMN_LOAN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_FOREIGN_FRIEND_ID + " INTEGER REFERENCES friend ON DELETE CASCADE, " +
                    COLUMN_FOREIGN_ITEM_ID + " INTEGER REFERENCES item ON DELETE CASCADE, " +
                    COLUMN_INDATE + " TEXT, " +
                    COLUMN_OUTDATE + " TEXT, " +
                    COLUMN_COMENTS + " TEXT, " +
                    COLUMN_LOAN_IMAGE + " TEXT);";


}
