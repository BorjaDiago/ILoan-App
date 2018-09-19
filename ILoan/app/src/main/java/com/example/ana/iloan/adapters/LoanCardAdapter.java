package com.example.ana.iloan.adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ana.iloan.R;
import com.example.ana.iloan.beans.Friend;
import com.example.ana.iloan.beans.Item;
import com.example.ana.iloan.beans.Loan;
import com.example.ana.iloan.database.DataBaseILoan;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class LoanCardAdapter extends ArrayAdapter<Loan> {

    private List<Loan> loans;

    public LoanCardAdapter(@NonNull Context context, List<Loan> loans) {
        super(context, R.layout.loan_card, loans);
        this.loans = loans;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View itemView = convertView;
        if(itemView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            itemView = inflater.inflate(R.layout.item_card, null);
            viewHolderLoan vh = new viewHolderLoan();
            vh.loanImage = (ImageView) itemView.findViewById(R.id.image_loan_card);
            vh.inDate = (TextView) itemView.findViewById(R.id.in_date_loan_card);
            vh.endDate = (TextView) itemView.findViewById(R.id.end_date_loan_card);
            vh.friendName = (TextView) itemView.findViewById(R.id.friend_name_loan_card);
            vh.itemTitle = (TextView) itemView.findViewById(R.id.title_item_loan_card);
            vh.kind = (TextView) itemView.findViewById(R.id.kind_loan_card);
            vh.genre = (TextView) itemView.findViewById(R.id.genre_loan_card);
            vh.comments = (TextView) itemView.findViewById(R.id.comments_loan_card);
            itemView.setTag(vh);
        }
        else{
            itemView = convertView;
        }
        viewHolderLoan vh2 = (viewHolderLoan) itemView.getTag();
        if(loans.get(position).getImage() != null && !loans.get(position).getImage().isEmpty()){
            File f = new File(loans.get(position).getImage());
            Picasso.with(getContext()).load(f).into(vh2.loanImage);
        }
        vh2.inDate.setText(getItem(position).getInDate());
        vh2.endDate.setText(getItem(position).getOutTime());
        Friend friend = getFriend(getItem(position).getId_friend());
        vh2.friendName.setText(friend.getName());
        vh2.phoneButton.setText(friend.getPhone());
        Item item = getItems(getItem(position).getId_friend());
        vh2.itemTitle.setText(item.getTitle());
        vh2.kind.setText(item.getKind());
        vh2.genre.setText(item.getGenre());
        return (itemView);

    }


    private static  class viewHolderLoan{
        private ImageView loanImage;
        private TextView loanTitle, inDate, endDate, friendTitle, friendName, itemTitleTag, itemTitle, kind, genre, comments;
        private Button itemButon, phoneButton;
        private Item item;
    }
    private Friend getFriend(int id_friend) {
        DataBaseILoan dbi = new DataBaseILoan(getContext());
        SQLiteDatabase db = dbi.getReadableDatabase();
        Friend friend = new Friend();
        String[] args = new String[] {Integer.toString(id_friend)};
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM friend WHERE _id=?", args);
            while (cursor.moveToFirst()){
                friend.setId(cursor.getInt(0));
                friend.setName(cursor.getString(1));
                friend.setSurname(cursor.getString(2));
                friend.setPhone(cursor.getString(3));
                friend.setImage(cursor.getString(4));
            }
            cursor.close();
        }
        catch (Exception e){
            Toast.makeText(getContext(), "ERROR in getFriend", Toast.LENGTH_LONG).show();
        }
        return friend;
    }
    private Item getItems(int id_item) {
        DataBaseILoan dbi = new DataBaseILoan(getContext());
        SQLiteDatabase db = dbi.getReadableDatabase();
        Item item = new Item();
        String[] args = new String[] {Integer.toString(id_item)};
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM item WHERE _id=?", args);
            while (cursor.moveToFirst()){
                item.setId(cursor.getInt(0));
                item.setTitle(cursor.getString(1));
                item.setKind(cursor.getString(2));
                item.setGenre(cursor.getString(3));
                item.setImage(cursor.getString(4));
                item.setYear(cursor.getString(5));
            }
            cursor.close();
        }
        catch (Exception e){
            Toast.makeText(getContext(), "ERROR in getItem", Toast.LENGTH_LONG).show();
        }
        return item;
    }
}
