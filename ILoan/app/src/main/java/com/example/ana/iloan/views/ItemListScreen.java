package com.example.ana.iloan.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.ana.iloan.R;
import com.example.ana.iloan.adapters.ItemAdapter;
import com.example.ana.iloan.beans.Item;
import com.example.ana.iloan.database.ItemDao;

import java.util.ArrayList;

public class ItemListScreen extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_screen);
        listView = (ListView) findViewById(R.id.list_one);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Item item = (Item) adapterView.getItemAtPosition(i);
                if(item != null){
                    Intent intent = new Intent(ItemListScreen.this, ItemCardView.class);
                    intent.putExtra("id", item.getId());
                    intent.putExtra("title", item.getTitle());
                    intent.putExtra("kind", item.getKind());
                    intent.putExtra("genre", item.getGenre());
                    intent.putExtra("image", item.getImage());
                    intent.putExtra("year", item.getYear());
                    startActivity(intent);
                }
            }
        });
        ImageButton imageButton = (ImageButton) findViewById(R.id.fab);
        ImageButton backImageButton = (ImageButton) findViewById(R.id.back_fab);
        ArrayList<Item> items = ItemDao.getItems(getApplicationContext());
        renderList(items);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ItemListScreen.this, ILoanItems.class);
                startActivity(intent1);
                finish();
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
        ArrayList<Item> items = ItemDao.getItems(getApplicationContext());
        renderList(items);
    }

    private void renderList(ArrayList<Item> itemList) {
        listView.setAdapter(new ItemAdapter(getApplicationContext(), itemList));
    }
}
