package com.example.ana.iloan.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.ana.iloan.R;
import com.example.ana.iloan.adapters.FriendAdapter;
import com.example.ana.iloan.beans.Friend;
import com.example.ana.iloan.database.FriendDao;

import java.util.ArrayList;
import java.util.List;

public class FriendListScreen extends AppCompatActivity{
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_screen);
        listView = (ListView) findViewById(R.id.list_one);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Friend friend = (Friend) adapterView.getItemAtPosition(i);
                if(friend != null){
                    Intent intent = new Intent(FriendListScreen.this, FriendCardView.class);
                    intent.putExtra("id", friend.getId());
                    intent.putExtra("name", friend.getName());
                    intent.putExtra("surname", friend.getSurname());
                    intent.putExtra("phone", friend.getPhone());
                    intent.putExtra("image", friend.getImage());
                    startActivity(intent);
                }
            }
        });
        ImageButton imageButton = (ImageButton) findViewById(R.id.fab);
        ImageButton backImageButton = (ImageButton) findViewById(R.id.back_fab);

        ArrayList<Friend> friends = FriendDao.getFriends(getApplicationContext());
        renderList(friends);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(FriendListScreen.this, ILoanFriends.class);
                startActivity(intent1);
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
        ArrayList<Friend> friends = FriendDao.getFriends(getApplicationContext());
        renderList(friends);

    }

    private void renderList(List<Friend> friends){
        listView.setAdapter(new FriendAdapter(getApplicationContext(), friends));
    }
}
