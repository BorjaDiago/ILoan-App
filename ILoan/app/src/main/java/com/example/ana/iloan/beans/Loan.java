package com.example.ana.iloan.beans;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ana.iloan.database.DataBaseILoan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Loan {
    private int id;
    private String inDate;
    private String outTime;
    private int id_friend;
    private int id_item;
    private String comments;
    private String image;

    public Loan(String inDate, String outTime, int id_friend, int id_item, String comments, String image){
        this.inDate = inDate;
        this.outTime = outTime;
        this.id_friend = id_friend;
        this.id_item = id_item;
        this.comments = comments;
        this.image = image;
    }
    public Loan(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInDate() {
        return inDate;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public int getId_friend() {
        return id_friend;
    }

    public void setId_friend(int id_friend) {
        this.id_friend = id_friend;
    }

    public int getId_item() {
        return id_item;
    }

    public void setId_item(int id_item) {
        this.id_item = id_item;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static Set<Integer> getInActiveLoans(Context context) {
        DataBaseILoan dbi = new DataBaseILoan(context);
        SQLiteDatabase db = dbi.getReadableDatabase();
        Set<Integer> loanList = new HashSet<>();
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM loan", null);
            while(cursor.moveToNext()){
                if(!isBeforeOutDate((cursor.getString(4)))){
                    loanList.add(cursor.getInt(2));
                }
                else{
                    if(loanList.contains(cursor.getString(4))){
                        loanList.remove(cursor.getString(4));
                    }
                }
            }
            cursor.close();
            return loanList;
        }catch (Exception e){
            return null;
        }
    }

    private static boolean isBeforeOutDate(String date1) throws ParseException {
        String today = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
        date1 = date1.trim();
        Date todDate = new SimpleDateFormat("dd/MM/yyyy").parse(today);
        Date date1Date = new SimpleDateFormat("dd/MM/yyyy").parse(date1);
        return !date1Date.after(todDate) && !date1.equals(today);
    }
}
