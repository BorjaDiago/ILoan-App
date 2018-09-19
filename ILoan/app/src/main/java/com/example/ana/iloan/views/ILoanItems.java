package com.example.ana.iloan.views;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ana.iloan.R;
import com.example.ana.iloan.database.DataBaseILoan;
import com.example.ana.iloan.database.ItemDao;

import java.io.ByteArrayOutputStream;

public class ILoanItems extends AppCompatActivity {

    private EditText title, kind, genre, year;
    ImageView image;
    DataBaseILoan dbi = new DataBaseILoan(this);
    String url;
    String data[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iloan_items);
        Button add = (Button) findViewById(R.id.button);
        title = (EditText) findViewById(R.id.title_input);
        kind = (EditText) findViewById(R.id.kind_input);
        genre = (EditText) findViewById(R.id.genre_input);
        image = (ImageView) findViewById(R.id.imagen_add_item);
        year = (EditText) findViewById(R.id.year_input);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check = checkParams(title.getText().toString(), kind.getText().toString());
                if(check){
                    data[0] = title.getText().toString();
                    data[1] = kind.getText().toString();
                    data[2] = genre.getText().toString();
                    data[3] = url;
                    data[4] = year.getText().toString();
                    boolean right = ItemDao.addItem(data, dbi);
                    if(right){
                        Toast.makeText(getApplicationContext(), "Item insertado correctamente", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ILoanItems.this, ItemListScreen.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Fallo al ingresar en base de datos", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private boolean checkParams(String title, String kind){
        if(title.equals("") || title.isEmpty()){
            Toast.makeText(getApplicationContext(), "El título debe ser descriptivo", Toast.LENGTH_LONG).show();
        }
        else if(kind.equals("") || kind.isEmpty()){
            Toast.makeText(getApplicationContext(), "El ítem debe tener tipo", Toast.LENGTH_LONG).show();
        }
        else{
            return true;
        }
        return false;
    }
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            image.setImageBitmap(imageBitmap);
            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(getApplicationContext(), imageBitmap);
            // CALL THIS METHOD TO GET THE ACTUAL PATH
            url = getRealPathFromURI(tempUri);
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
}
