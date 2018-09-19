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
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class FriendCardAdapter extends ArrayAdapter<Friend> {
    private List<Friend> friends;


    public FriendCardAdapter(Context context, List<Friend> friends){
        super(context, R.layout.friend_card, friends);
        this.friends = friends;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if(itemView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            itemView = inflater.inflate(R.layout.friend_card, null);
            viewHolder vh = new viewHolder();
            vh.image = (ImageView) itemView.findViewById(R.id.frien_card_image);
            vh.name = (TextView) itemView.findViewById(R.id.friend_card_name);
            vh.surname = (TextView) itemView.findViewById(R.id.friend_card_surname);
            vh.phone = (TextView) itemView.findViewById(R.id.friend_card_phone);
            itemView.setTag(vh);
        }
        else{
            itemView = convertView;
        }
        viewHolder vh2 = (viewHolder) itemView.getTag();
        if(friends.get(position).getImage() != null && !friends.get(position).getImage().isEmpty()){
            File f = new File(friends.get(position).getImage());
            Picasso.with(getContext()).load(f).into(vh2.image);
        }
        vh2.name.setText(getItem(position).getName());
        vh2.surname.setText(getItem(position).getName());
        vh2.phone.setText(getItem(position).getPhone());

        return (itemView);
    }
    static class viewHolder{
        ImageView image;
        TextView name, surname, phone;
    }
}
