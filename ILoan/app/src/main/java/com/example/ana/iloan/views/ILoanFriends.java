package com.example.ana.iloan.views;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.ana.iloan.database.FriendDao;

import java.io.ByteArrayOutputStream;


public class ILoanFriends extends AppCompatActivity {

    EditText campoNombre;
    EditText campoApellido;
    EditText campoTelefono;
    ImageView friendImage;
    Button boton, imageButton;
    DataBaseILoan dbi;
    String url;
    String data[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iloan_friends);

        campoNombre = (EditText) findViewById(R.id.i_loan_friends_name);
        campoApellido = (EditText) findViewById(R.id.i_loan_friends_surname);
        campoTelefono = (EditText) findViewById(R.id.i_loan_friends_phone);
        boton = (Button) findViewById(R.id.add_friend_button);
        imageButton = (Button) findViewById(R.id.get_friend_image_button_friends);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
        friendImage = (ImageView) findViewById(R.id.image_friend_add_card);
        dbi = new DataBaseILoan(getApplicationContext());

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data[0] = campoNombre.getText().toString();
                data[1] = campoApellido.getText().toString();
                data[2] = campoTelefono.getText().toString();
                data[3] = url;
                boolean rigth = checkParams(campoNombre.getText().toString(), campoTelefono.getText().toString());
                if (rigth) {
                    boolean insert = FriendDao.addFriend(data, dbi);
                    if (insert) {
                        Toast.makeText(getApplicationContext(), "Friend has been added", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Fallo en la inserción.", Toast.LENGTH_LONG).show();
                    }
                    limpiar();
                }
            }
        });

    }

    private void limpiar() {
        campoNombre.setText("");
        campoApellido.setText("");
        campoTelefono.setText("");
    }

    private boolean checkParams(String title, String kind) {
        if (title.equals("") || title.isEmpty()) {
            Toast.makeText(getApplicationContext(), "El título debe ser descriptivo", Toast.LENGTH_LONG).show();
        } else if (kind.equals("") || kind.isEmpty()) {
            Toast.makeText(getApplicationContext(), "El ítem debe tener tipo", Toast.LENGTH_LONG).show();
        } else {
            return true;
        }
        return false;
    }

    private void consultar() {
        SQLiteDatabase db = dbi.getReadableDatabase();
        String[] campos = {"name", "surname", "phone", "friend_image"};

        try {
            Cursor cursor = db.query("friend", campos, null, null, null, null, null);
            cursor.moveToFirst();
            campoNombre.setText(cursor.getString(0));
            campoApellido.setText(cursor.getString(1));
            campoTelefono.setText(cursor.getString(2));

            cursor.close();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Fallo en la consulta.", Toast.LENGTH_LONG).show();
            limpiar();
        }
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            friendImage.setImageBitmap(imageBitmap);
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
