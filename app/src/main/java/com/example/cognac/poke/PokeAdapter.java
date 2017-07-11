package com.example.cognac.poke;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
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

public class PokeAdapter extends RecyclerView.Adapter<PokeAdapter.ViewHolder>{

    ArrayList<Poke> pokemon;
    Context context;
    off zz;

    public PokeAdapter( Context context,ArrayList<Poke> pokemon, off zz) {
        this.pokemon = pokemon;
        this.zz = zz;
        this.context = context;
    }

    @Override
    public PokeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.poke_item,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.text.setText(pokemon.get(position).getName());
        Glide.with(context).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+pokemon.get(position).getNumber()+".png").centerCrop().crossFade().into(holder.image);
    }


    @Override
    public int getItemCount() {
        return pokemon.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView image;
        private TextView text;

        public ViewHolder(View ItemView){
            super(ItemView);

            image = (ImageView)ItemView.findViewById(R.id.image);
            text = (TextView)ItemView.findViewById(R.id.name);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pokemonNum = pokemon.get(getAdapterPosition()).getNumber();;
                    String uurl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+pokemon.get(getAdapterPosition()).getNumber()+".png";
                    String name = pokemon.get(getAdapterPosition()).getName();
                    FragmentManager fm = ((AppCompatActivity)context).getSupportFragmentManager();
                    PokemonInfo pk = new PokemonInfo();
                    Bundle bundle = new Bundle();
                    bundle.putInt("number",pokemonNum);
                    bundle.putString("image",uurl);
                    bundle.putString("name",name);
                    zz.iOff();
                    pk.setArguments(bundle);
                    fm.beginTransaction().replace(R.id.frame,pk).addToBackStack("pokemonInfo").commit();
                }
            });
        }
    }
    public interface off{
        public void iOff();
    }
}
