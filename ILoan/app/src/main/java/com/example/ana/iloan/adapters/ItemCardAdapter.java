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
import com.example.ana.iloan.beans.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemCardAdapter extends ArrayAdapter<Item> {
    private List<Item> items;

    public ItemCardAdapter(@NonNull Context context, List<Item> items) {
        super(context, R.layout.item_card, items);
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if(itemView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            itemView = inflater.inflate(R.layout.item_card, null);
            viewHolder vh = new viewHolder();
            vh.image = (ImageView) itemView.findViewById(R.id.image_card_item);
            vh.title = (TextView) itemView.findViewById(R.id.title_card_item);
            vh.kind = (TextView) itemView.findViewById(R.id.kind_card_item);
            vh.genre = (TextView) itemView.findViewById(R.id.genre_card_item);
            vh.year = (TextView) itemView.findViewById(R.id.year_card_item);
            itemView.setTag(vh);
        }
        else{
            itemView = convertView;
        }
        viewHolder vh2 = (viewHolder) itemView.getTag();
        if(items.get(position).getImage() != null && !items.get(position).getImage().isEmpty()){
            Picasso.with(getContext()).load(items.get(position).getImage()).into(vh2.image);
        }
        vh2.title.setText(getItem(position).getTitle());
        vh2.kind.setText(getItem(position).getKind());
        vh2.genre.setText(getItem(position).getTitle());
        vh2.year.setText(getItem(position).getYear());
        return (itemView);
    }
    static class viewHolder{
        ImageView image;
        TextView title, kind, genre, year;
    }
}
