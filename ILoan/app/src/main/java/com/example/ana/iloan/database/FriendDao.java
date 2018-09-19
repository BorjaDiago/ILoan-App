package com.example.ana.iloan.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ana.iloan.beans.Friend;

import java.util.ArrayList;

public class FriendDao {

    public static Friend getFriend(int id_friend, Context context) {
        DataBaseILoan dbi = new DataBaseILoan(context);
        SQLiteDatabase db = dbi.getReadableDatabase();
        Friend friend = new Friend();
        String[] args = new String[] {Integer.toString(id_friend)};
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM friend WHERE _id=?", args);
            while (cursor.moveToNext()){
                friend.setId(cursor.getInt(0));
                friend.setName(cursor.getString(1));
                friend.setSurname(cursor.getString(2));
                friend.setPhone(cursor.getString(3));
                friend.setImage(cursor.getString(4));
            }
            cursor.close();
        }
        catch (Exception e){
            return null;
        }
        return friend;
    }
    public static ArrayList<Friend> getFriends(Context context) {
        DataBaseILoan dbi = new DataBaseILoan(context);
        SQLiteDatabase db = dbi.getReadableDatabase();
        Friend friend;
        ArrayList<Friend> friendList = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM friend ORDER BY name", null);
            while(cursor.moveToNext()){
                friend = new Friend();
                friend.setId(cursor.getInt(0));
                friend.setName(cursor.getString(1));
                friend.setSurname(cursor.getString(2));
                friend.setPhone(cursor.getString(3));
                friend.setImage(cursor.getString(4));

                friendList.add(friend);
            }
            cursor.close();
            return friendList;
        }
        catch (Exception e) {
            return null;
        }
    }
    public static boolean addFriend(String data[], DataBaseILoan dbi) {
        try {
            SQLiteDatabase db = dbi.getWritableDatabase();
            if (db != null) {
                ContentValues cv = new ContentValues();
                cv.put("name", data[0]);
                cv.put("surname", data[1]);
                cv.put("phone", data[2]);
                cv.put("friend_image", data[3]);
                db.insert("friend", null, cv);
                db.close();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static void deleteFriend(int id, DataBaseILoan dbi) {
        SQLiteDatabase db = dbi.getWritableDatabase();
        try{
            db.delete("friend", "_id='"+id+"'", null);
            db.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
