package com.example.ana.iloan.views;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ana.iloan.R;
import com.example.ana.iloan.adapters.LoanAdapter;
import com.example.ana.iloan.beans.Friend;
import com.example.ana.iloan.beans.Item;
import com.example.ana.iloan.beans.Loan;
import com.example.ana.iloan.database.DataBaseILoan;
import com.example.ana.iloan.database.FriendDao;
import com.example.ana.iloan.database.ItemDao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class LoanListScreen extends AppCompatActivity {

    private ListView listView;
    private DataBaseILoan dbi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_screen);

        dbi = new DataBaseILoan(getApplicationContext());
        listView = (ListView) findViewById(R.id.list_one);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Loan loan = (Loan) adapterView.getItemAtPosition(i);
                if(loan != null){
                    Intent intent = new Intent(LoanListScreen.this, LoanCardView.class);
                    intent.putExtra("id", loan.getId());
                    intent.putExtra("in_date", loan.getInDate());
                    intent.putExtra("out_date", loan.getOutTime());
                    intent.putExtra("id_friend", loan.getId_friend());
                    intent.putExtra("id_item", loan.getId_item());
                    intent.putExtra("comments", loan.getComments());
                    intent.putExtra("image", loan.getImage());
                    startActivity(intent);
                }
            }
        });
        ImageButton imageButton = (ImageButton) findViewById(R.id.fab);
        ImageButton backImageButton = (ImageButton) findViewById(R.id.back_fab);
        getLoans();


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Friend> friendRecords = FriendDao.getFriends(getApplicationContext());
                ArrayList<Item> itemRecords = ItemDao.getItemsAvailable(getApplicationContext());
                Boolean hasFriends = (friendRecords!=null && !friendRecords.isEmpty());
                Boolean hasItems = (itemRecords != null && !itemRecords.isEmpty());
                if(hasFriends && !hasItems){
                    Toast.makeText(getApplicationContext(), "Impossible to add Loan without items available", Toast.LENGTH_LONG).show();
                }
                else if(!hasFriends && !hasItems){
                    Toast.makeText(getApplicationContext(), "Impossible to add Loan without items and friends", Toast.LENGTH_LONG).show();
                }
                else if(!hasFriends && hasItems){
                    Toast.makeText(getApplicationContext(), "Impossible to add Loan without friends", Toast.LENGTH_LONG).show();
                }
                else{
                    Intent intent1 = new Intent(LoanListScreen.this, ILoans.class);
                    startActivity(intent1);
                    finish();
                }
            }
        });
        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    protected void onRestart() {
        super.onRestart();
        getLoans();
    }

    private void getLoans() {
        SQLiteDatabase db = dbi.getReadableDatabase();
        Loan loan;
        ArrayList<Loan> loanList = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM loan", null);
            while(cursor.moveToNext()){
                loan = new Loan();
                loan.setId(cursor.getInt(0));
                loan.setId_friend(cursor.getInt(1));
                loan.setId_item(cursor.getInt(2));
                loan.setInDate((cursor.getString(3)));
                loan.setOutTime((cursor.getString(4)));
                loan.setComments(cursor.getString(5));
                loan.setImage(cursor.getString(6));

                loanList.add(loan);
            }
            cursor.close();
            renderList(loanList);
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "ERROR in database", Toast.LENGTH_LONG).show();
        }
    }
    private void renderList(ArrayList<Loan> loanList) {
        listView.setAdapter(new LoanAdapter(getApplicationContext(), loanList));
    }
}
