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
import android.widget.Toast;

import com.example.ana.iloan.R;
import com.example.ana.iloan.beans.Item;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class ItemAdapter extends ArrayAdapter<Item>{
    private List<Item> items;

    public ItemAdapter(@NonNull Context context, List<Item> items){
        super(context, R.layout.item_list, items);
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            itemView = inflater.inflate(R.layout.item_list, null);
            listViewItems lvi = new listViewItems();
            lvi.title = (TextView) itemView.findViewById(R.id.item_title);
            lvi.kind = (TextView) itemView.findViewById(R.id.item_kind);
            lvi.image = (ImageView) itemView.findViewById(R.id.item_image);

            itemView.setTag(lvi);
        }
        else{
            itemView = convertView;
        }
        listViewItems lvi2 = (listViewItems) itemView.getTag();
        if(items.get(position).getImage() != null && !items.get(position).getImage().isEmpty()){
            File f = new File(items.get(position).getImage());
            Picasso.with(getContext()).load(f).into(lvi2.image);
        }
        try {
            lvi2.title.setText(getItem(position).getTitle());
            lvi2.kind.setText(getItem(position).getKind());
        }
        catch (NullPointerException e){
            Toast.makeText(getContext(), "Fallo al asignar los datos", Toast.LENGTH_LONG).show(); }

        return (itemView);
    }
    class listViewItems{
        TextView title;
        TextView kind;
        ImageView image;
    }

}
