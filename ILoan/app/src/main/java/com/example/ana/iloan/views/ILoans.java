package com.example.ana.iloan.views;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ana.iloan.R;
import com.example.ana.iloan.beans.Friend;
import com.example.ana.iloan.beans.Item;
import com.example.ana.iloan.database.DataBaseILoan;
import com.example.ana.iloan.database.FriendDao;
import com.example.ana.iloan.database.ItemDao;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ILoans extends AppCompatActivity {

    EditText inDate, endDate, comments;
    Spinner friends, items;
    ImageButton addButton;
    ImageView loanNewImage;
    Button loanImage;
    String url;
    final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iloans);

        inDate = (EditText) findViewById(R.id.start_loan_add_loan);
        inDate.setInputType(InputType.TYPE_NULL);
        endDate = (EditText) findViewById(R.id.end_loan_add_loan);
        endDate.setInputType(InputType.TYPE_NULL);
        friends = (Spinner) findViewById(R.id.spinner_id_friend);
        items = (Spinner) findViewById(R.id.spinner_id_item);
        comments = (EditText) findViewById(R.id.comments_add_loan);
        loanImage = (Button) findViewById(R.id.image_loan_add_loan);
        addButton = (ImageButton) findViewById(R.id.add_loan_button);
        loanNewImage = (ImageView) findViewById(R.id.image_add_loan);


        final DatePickerDialog.OnDateSetListener inDateF = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                updateInDate(inDate);
            }
        };
        final DatePickerDialog.OnDateSetListener endDateF = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                updateInDate(endDate);
            }
        };
        inDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ILoans.this, inDateF, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ILoans.this, endDateF, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        List<Friend> friendList = FriendDao.getFriends(getApplicationContext());
        List<String> friendNames = new ArrayList<>();
        final List<Integer> friendIds = new ArrayList<>();
        for(Friend f : friendList){
            friendNames.add(f.getName());
            friendIds.add(f.getId());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, friendNames);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        friends.setAdapter(arrayAdapter);

        //List<Item> itemList = getItems();
        List<Item> itemList = ItemDao.getItemsAvailable(getApplicationContext());
        List<String> itemNames = new ArrayList<>();
        final List<Integer> itemIds = new ArrayList<>();
        for(Item i : itemList){
            itemNames.add(i.getTitle());
            itemIds.add(i.getId());
        }

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, itemNames);
        arrayAdapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        items.setAdapter(arrayAdapter1);




        loanImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int positionName = friends.getSelectedItemPosition();
                final int idFriend = friendIds.get(positionName);
                int positionItem = items.getSelectedItemPosition();
                final int idItem = itemIds.get(positionItem);
                boolean right = checkParams(inDate.getText().toString(), idFriend, idItem);
                if(right){
                    boolean ok = addLoan(idFriend, idItem);
                    if(ok){
                        Toast.makeText(getApplicationContext(), "Loan started", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Loan insert failed", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
    private boolean addLoan(int idFriend, int idItem){
        try {
            DataBaseILoan dbi = new DataBaseILoan(getApplicationContext());
            SQLiteDatabase db = dbi.getWritableDatabase();
            if(db != null){
                ContentValues cv = new ContentValues();
                cv.put("id_friend", idFriend);
                cv.put("id_item", idItem);
                cv.put("in_date", inDate.getText().toString());
                cv.put("out_date", endDate.getText().toString());
                cv.put("coments", comments.getText().toString());
                cv.put("loan_image", url);
                db.insert("loan", null, cv);
                db.close();
            }
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    private boolean checkParams(String inDate, int idFriend, int idItem){
        if(inDate.isEmpty()){
            Toast.makeText(getApplicationContext(), "Loan must have initial date", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(idFriend < 0){
            Toast.makeText(getApplicationContext(), "Loan must have a reciver", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(idItem < 0){
            Toast.makeText(getApplicationContext(), "Loan must have a borrowed item", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    private void updateInDate(EditText date){
        String dateFormat = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.FRANCE);
        date.setText(simpleDateFormat.format(calendar.getTime()));
    }
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            loanNewImage.setImageBitmap(imageBitmap);
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
