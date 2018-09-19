package com.example.ana.iloan.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ana.iloan.R;
import com.example.ana.iloan.beans.Friend;
import com.example.ana.iloan.beans.Loan;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class LoanAdapter extends ArrayAdapter<Loan>{

    private List<Loan> loans;

    public LoanAdapter(@NonNull Context context, List<Loan> loans) {
        super(context, R.layout.loan_list, loans);
        this.loans = loans;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView;
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            itemView = inflater.inflate(R.layout.loan_list, null);
            listViewLoans lvl = new listViewLoans();
            lvl.image = (ImageView) itemView.findViewById(R.id.loan_image_loan_list);
            lvl.inDate = (TextView) itemView.findViewById(R.id.loan_in_date_loan_card);
            lvl.name = (TextView) itemView.findViewById(R.id.friend_name_loan_card);

            itemView.setTag(lvl);
        }
        else{
            itemView = convertView;
        }
        listViewLoans lvl2 = (listViewLoans) itemView.getTag();
        if(loans.get(position).getImage() != null && !loans.get(position).getImage().isEmpty()){
            File f = new File(loans.get(position).getImage());
            Picasso.with(getContext()).load(f).into(lvl2.image);
        }
        Friend friend = Friend.getFriend(getItem(position).getId_friend(), getContext());
        lvl2.name.setText(friend.getName());
        lvl2.inDate.setText(getItem(position).getInDate());
        return (itemView);
    }

    class listViewLoans{
        ImageView image;
        TextView inDate, name;
    }
}
