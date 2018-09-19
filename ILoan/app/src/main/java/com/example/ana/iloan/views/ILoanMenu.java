package com.example.ana.iloan.views;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.ana.iloan.R;
import com.example.ana.iloan.database.DataBaseILoan;

public class ILoanMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iloan_menu);

        DataBaseILoan dbi = new DataBaseILoan(this);
        SQLiteDatabase db = dbi.getWritableDatabase();

//        if(db != null) {
//            ContentValues cv = new ContentValues();
//            cv.put("name", "Borja");
//            cv.put("surname", "Diago");
//            cv.put("phone", "666666666");
//            cv.put("friend_image", "prueba");
//            db.insert("friend", null, cv);
//            db.close();
//        }
//        db = dbi.getWritableDatabase();
//        if(db != null) {
//            ContentValues cv = new ContentValues();
//            cv.put("title", "Libro 1");
//            cv.put("kind", "book");
//            cv.put("genre", "action");
//            cv.put("item_image", "prueba");
//            cv.put("year", "1982");
//            db.insert("item", null, cv);
//            db.close();
//        }

        Button friends =(Button) findViewById(R.id.FriendButton);
        Button items = (Button)findViewById(R.id.ItemButton);
        Button loans = (Button)findViewById(R.id.LoanButton);

        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ILoanMenu.this, FriendListScreen.class);
                startActivity(intent);
            }
        });

        items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ILoanMenu.this, ItemListScreen.class);
                startActivity(intent);
            }
        });

        loans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ILoanMenu.this, LoanListScreen.class);
                startActivity(intent);
            }
        });
    }
}
