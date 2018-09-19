package com.example.ana.iloan.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ana.iloan.R;
import com.example.ana.iloan.beans.Friend;
import com.example.ana.iloan.beans.Item;
import com.example.ana.iloan.beans.Loan;
import com.example.ana.iloan.database.DataBaseILoan;
import com.example.ana.iloan.database.FriendDao;
import com.example.ana.iloan.database.ItemDao;
import com.squareup.picasso.Picasso;

import java.io.File;

public class LoanCardView extends AppCompatActivity {

    private ImageView loanImage;
    private TextView inDate, endDate, friendName, itemTitle, comments;
    private Button itemButon, phoneButton;
    private Item item;
    private ImageButton deleteLoan, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_card_view);
        assign();

        final Intent intent = getIntent();
        final Loan loan = new Loan();
        loan.setId(intent.getIntExtra("id", -1));
        loan.setInDate(intent.getStringExtra("in_date"));
        loan.setOutTime(intent.getStringExtra("out_date"));
        loan.setId_friend(intent.getIntExtra("id_friend", -1));
        loan.setId_item(intent.getIntExtra("id_item", -1));
        loan.setComments(intent.getStringExtra("comments"));
        loan.setImage(intent.getStringExtra("image"));

        inDate.setText(loan.getInDate());
        endDate.setText(loan.getOutTime());
        comments.setText(loan.getComments());
        if(loan.getImage() != null && !loan.getImage().isEmpty()){
            File f = new File(loan.getImage());
            Picasso.with(getApplicationContext()).load(f).into(loanImage);
        }
        final Friend friend = getFriend(loan.getId_friend());
        friendName.setText(friend.getName());
        phoneButton.setText(friend.getPhone());
        item = getItem(loan.getId_item());
        itemTitle.setText(item.getTitle());
        final String phone = friend.getPhone();

        deleteLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( loan.getId() != -1){
                    onDeletePressed(loan.getId());
                }
                else{
                    Toast.makeText(getApplicationContext(), "Id incorrecto", Toast.LENGTH_LONG).show();
                }
            }
        });

        itemButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoanCardView.this, ItemCardView.class);
                intent.putExtra("id", item.getId());
                intent.putExtra("title", item.getTitle());
                intent.putExtra("kind", item.getKind());
                intent.putExtra("genre", item.getGenre());
                intent.putExtra("image", item.getImage());
                intent.putExtra("year", item.getYear());
                startActivity(intent);
            }
        });

        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Intent.ACTION_DIAL);
                intent1.setData(Uri.parse("tel:" + phone));
                if (intent1.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent1);
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private Friend getFriend(int id_friend) {
        Friend friend = new Friend();
        try{
            friend = FriendDao.getFriend(id_friend, getApplicationContext());
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "ERROR in getFriend", Toast.LENGTH_LONG).show();
        }
        return friend;
    }
    private Item getItem(int id_item) {
        Item item = new Item();
        try {
            item = ItemDao.getItem(id_item, getApplicationContext());
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "ERROR in getItem", Toast.LENGTH_LONG).show();
        }
        return item;
    }
    private void deleteLoan(int id) {
        DataBaseILoan dbi = new DataBaseILoan(getApplicationContext());
        SQLiteDatabase db = dbi.getWritableDatabase();
        try{
            db.delete("loan", "_id='"+id+"'", null);
            db.close();
            Toast.makeText(getApplicationContext(), "Loan deleted", Toast.LENGTH_LONG).show();
            finish();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
        }
    }
    private void onDeletePressed(final int id){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoanCardView.this);
        builder.setMessage("Do you want to delete?").setTitle("Delete confirmation");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteLoan(id);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void assign(){
        loanImage = (ImageView) findViewById(R.id.image_loan_card_view);
        inDate = (TextView) findViewById(R.id.in_date_loan_card_view);
        endDate = (TextView) findViewById(R.id.end_date_loan_card_view);
        friendName = (TextView) findViewById(R.id.friend_name_loan_card_view);
        itemTitle = (TextView) findViewById(R.id.title_item_loan_card_view);
        comments = (TextView) findViewById(R.id.comments_loan_card_view);
        backButton = (ImageButton) findViewById(R.id.back_button_loan_card);
        itemButon = (Button) findViewById(R.id.item_button_loan_card_view);
        phoneButton = (Button) findViewById(R.id.phone_button_loan_card_view);
        deleteLoan = (ImageButton) findViewById(R.id.delete_button_loan_card);
    }
}
