package com.example.ana.iloan.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ana.iloan.beans.Item;
import com.example.ana.iloan.beans.Loan;

import java.util.ArrayList;
import java.util.Set;

public class ItemDao {
    public static ArrayList<Item> getItemsAvailable(Context context) {
        Set<Integer> noAvailableItems = Loan.getInActiveLoans(context);
        DataBaseILoan dbi = new DataBaseILoan(context);
        SQLiteDatabase db = dbi.getReadableDatabase();
        Item item;
        ArrayList<Item> itemList = new ArrayList<>();

        try{
            Cursor cursor = db.rawQuery("SELECT * FROM item ORDER BY title", null);
            while (cursor.moveToNext()){
                if(noAvailableItems.contains(cursor.getInt(0))){
                    continue;
                }
                item = new Item();
                item.setId(cursor.getInt(0));
                item.setTitle(cursor.getString(1));
                item.setKind(cursor.getString(2));
                item.setGenre(cursor.getString(3));
                item.setImage(cursor.getString(4));
                item.setYear(cursor.getString(5));

                itemList.add(item);
            }
            cursor.close();
            return itemList;
        }catch (Exception e){
            return null;
        }
    }

    public static ArrayList<Item> getItems(Context context) {
        DataBaseILoan dbi = new DataBaseILoan(context);
        SQLiteDatabase db = dbi.getReadableDatabase();
        Item item;
        ArrayList<Item> itemList = new ArrayList<>();

        try{
            Cursor cursor = db.rawQuery("SELECT * FROM item ORDER BY title", null);
            while (cursor.moveToNext()){

                item = new Item();
                item.setId(cursor.getInt(0));
                item.setTitle(cursor.getString(1));
                item.setKind(cursor.getString(2));
                item.setGenre(cursor.getString(3));
                item.setImage(cursor.getString(4));
                item.setYear(cursor.getString(5));

                itemList.add(item);
            }
            cursor.close();
            return itemList;
        }catch (Exception e){
            return null;
        }
    }

    public static Item getItem(int id_item, Context context) {
        DataBaseILoan dbi = new DataBaseILoan(context);
        SQLiteDatabase db = dbi.getReadableDatabase();
        Item item = new Item();
        String[] args = new String[] {Integer.toString(id_item)};
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM item WHERE _id=?", args);
            while (cursor.moveToNext()){
                item.setId(cursor.getInt(0));
                item.setTitle(cursor.getString(1));
                item.setKind(cursor.getString(2));
                item.setGenre(cursor.getString(3));
                item.setImage(cursor.getString(4));
                item.setYear(cursor.getString(5));
            }
            cursor.close();
        }
        catch (Exception e){
            return  null;
        }
        return item;
    }

    public static boolean addItem(String data[], DataBaseILoan dbi) {
        try{

            SQLiteDatabase db = dbi.getWritableDatabase();
            if(db != null) {
                ContentValues cv = new ContentValues();
                cv.put("title", data[0]);
                cv.put("kind", data[1]);
                cv.put("genre", data[2]);
                cv.put("item_image", data[3]);
                cv.put("year", data[4]);
                db.insert("item", null, cv);
                db.close();
            }
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    public static void deleteItem(int id, DataBaseILoan dbi) {

        SQLiteDatabase db = dbi.getWritableDatabase();
        try{
            db.delete("item", "_id='"+id+"'", null);
            db.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
