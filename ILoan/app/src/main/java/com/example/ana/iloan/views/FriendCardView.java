package com.example.ana.iloan.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ana.iloan.R;
import com.example.ana.iloan.beans.Friend;
import com.example.ana.iloan.database.DataBaseILoan;
import com.example.ana.iloan.database.FriendDao;
import com.squareup.picasso.Picasso;

import java.io.File;

public class FriendCardView extends AppCompatActivity {

    private TextView phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_card_view);
        ImageView friendImage = (ImageView) findViewById(R.id.friend_card_image_activity);
        TextView name = (TextView) findViewById(R.id.friend_card_name_activity);
        TextView surname = (TextView) findViewById(R.id.friend_card_surname_activity);
        phone = (TextView) findViewById(R.id.friend_card_phone_activity);
        ImageButton back = (ImageButton) findViewById(R.id.fab_friend_card);
        ImageButton delete = (ImageButton) findViewById(R.id.delete_friend_button);

        Intent intent = getIntent();
        final Friend friend = new Friend();

        friend.setId(intent.getIntExtra("id", -1));
        friend.setImage(intent.getStringExtra("image"));
        friend.setName(intent.getStringExtra("name"));
        friend.setSurname(intent.getStringExtra("surname"));
        friend.setPhone(intent.getStringExtra("phone"));
        friend.setImage(intent.getStringExtra("image"));

        name.setText(friend.getName());
        surname.setText(friend.getSurname());
        phone.setText(friend.getPhone());

        if(friend.getImage() != null && !friend.getImage().isEmpty()){
            File f = new File(friend.getImage());
            Picasso.with(getApplicationContext()).load(f).into(friendImage);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( friend.getId() != -1){
                    onDeletePressed(friend.getId());
                }
                else{
                    Toast.makeText(getApplicationContext(), "Id incorrecto", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void deleteFriend(int id) {
        DataBaseILoan dbi = new DataBaseILoan(getApplicationContext());
        try{
            FriendDao.deleteFriend(id, dbi);
            Toast.makeText(getApplicationContext(), "Friend deleted", Toast.LENGTH_LONG).show();
            finish();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
        }
    }
    private void onDeletePressed(final int id){
        AlertDialog.Builder builder = new AlertDialog.Builder(FriendCardView.this);
        builder.setMessage("Do you want to delete?").setTitle("Delete confirmation");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteFriend(id);
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
    public void callPhone(View view){
        Intent intent1 = new Intent(Intent.ACTION_DIAL);
        intent1.setData(Uri.parse("tel:" + phone.getText().toString()));
        if (intent1.resolveActivity(getPackageManager()) != null) {
            startActivity(intent1);
        }
    }
}
