package com.example.cognac.poke;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Cognac on 7/11/2017.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{
    ArrayList<Item> itemLsit;
    Context context;

    public ItemAdapter(ArrayList<Item> itemLsit, Context context) {
        this.itemLsit = itemLsit;
        this.context = context;
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout,parent,false);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.effect.setText(itemLsit.get(position).getEffect());
        holder.name.setText(itemLsit.get(position).getName());
        Glide.with(context).load(itemLsit.get(position).getImageUrl()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return itemLsit.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;
        TextView effect;

        public ViewHolder(View itemView) {
            super(itemView);

            image = (ImageView)itemView.findViewById(R.id.image);
            name = (TextView)itemView.findViewById(R.id.name);
            effect = (TextView)itemView.findViewById(R.id.effect);


        }
    }
}
