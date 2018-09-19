package com.example.ana.iloan.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ana.iloan.R;
import com.example.ana.iloan.beans.Item;
import com.example.ana.iloan.database.DataBaseILoan;
import com.example.ana.iloan.database.ItemDao;
import com.squareup.picasso.Picasso;

import java.io.File;

public class ItemCardView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_card_view);

        ImageView itemImage = (ImageView) findViewById(R.id.image_card_item_view);
        final TextView title = (TextView) findViewById(R.id.title_card_item_view);
        TextView kind = (TextView) findViewById(R.id.kind_card_item_view);
        TextView genre = (TextView) findViewById(R.id.genre_card_item_view);
        TextView year = (TextView) findViewById(R.id.year_card_item_view);

        ImageButton back = (ImageButton) findViewById(R.id.back_button_item_card_view);
        ImageButton delete = (ImageButton) findViewById(R.id.delete_button_item_card_view);

        Intent intent = getIntent();
        Item item = new Item();

        item.setId(intent.getIntExtra("id", -1));
        item.setTitle(intent.getStringExtra("title"));
        item.setKind(intent.getStringExtra("kind"));
        item.setGenre(intent.getStringExtra("genre"));
        item.setYear(intent.getStringExtra("year"));
        item.setImage(intent.getStringExtra("image"));

        final int id = item.getId();
        title.setText(item.getTitle());
        kind.setText(item.getKind());
        genre.setText(item.getGenre());
        year.setText(item.getYear());
        if(item.getImage() != null && !item.getImage().isEmpty()){
            File f = new File(item.getImage());
            Picasso.with(getApplicationContext()).load(f).into(itemImage);
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
                if( id != -1){
                    onDeletePressed(id);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Id incorrecto", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void deleteItem(int id) {

        DataBaseILoan dbi = new DataBaseILoan(ItemCardView.this);
        try{
            ItemDao.deleteItem(id, dbi);
            Toast.makeText(getApplicationContext(), "Item deleted", Toast.LENGTH_LONG).show();
            finish();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
        }
    }
    public void onDeletePressed(final int id){
        AlertDialog.Builder builder = new AlertDialog.Builder(ItemCardView.this);
        builder.setMessage("Do you want to delete?").setTitle("Delete confirmation");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteItem(id);
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
}
