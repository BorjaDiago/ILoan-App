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

public class FriendAdapter extends ArrayAdapter<Friend> {

    private List<Friend> friends;

    public FriendAdapter(@NonNull Context context, List<Friend> friends){
        super(context, R.layout.friend_list, friends);
        this.friends = friends;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            itemView = inflater.inflate(R.layout.friend_list, null);
            listViewFriends lvf = new listViewFriends();
            lvf.image = (ImageView) itemView.findViewById(R.id.friend_image_icon);
            lvf.name = (TextView) itemView.findViewById(R.id.friend_name);

            itemView.setTag(lvf);
        }
        else {
            itemView = convertView;
        }
        listViewFriends lvf2 = (listViewFriends) itemView.getTag();
        if(friends.get(position).getImage() != null && !friends.get(position).getImage().isEmpty()){
            File f = new File(friends.get(position).getImage());
            Picasso.with(getContext()).load(f).into(lvf2.image);
        }
        lvf2.name.setText(getItem(position).getName());
        return (itemView);
    }

    class listViewFriends{
        ImageView image;
        TextView name;
    }
}
