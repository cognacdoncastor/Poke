package com.example.cognac.poke;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Cognac on 7/11/2017.
 */

public class Pokemons extends Fragment implements PokemonInfo.Inter, PokeAdapter.off{

    private RecyclerView recyclerView;
    private PokeAdapter pokeAdapter;
    Query queryUtils;
    ArrayList<Poke> array;
    boolean next = true;
    int offset=0;
    Context cont;


    public Pokemons() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.pokemons, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);

        array = new ArrayList<>();

        queryUtils = new Query();
        cont = view.getContext();


        piste();

        array = queryUtils.getPokemons(getContext(), offset, pokeAdapter,new Query.onCallBack() {
            @Override
            public void onSuccsess(ArrayList<Poke> pokemon) {
                setView(pokemon);
            }
        });

        next = true;
        //  setView();

        return view;
    }

    private void piste() {
        PokemonInfo pok = new PokemonInfo();
        pok.setTargetFragment(this,0);

    }

    private void setView(ArrayList<Poke>pokemon) {
        pokeAdapter = new PokeAdapter(getContext(),pokemon,this);
        //     RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        final GridLayoutManager layoutManaager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(layoutManaager);
        recyclerView.setAdapter(pokeAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy>0){

                    int visibleItemCount = layoutManaager.getChildCount();
                    int totalItemCount = layoutManaager.getItemCount();
                    int pastVisibleItems = layoutManaager.findFirstVisibleItemPosition();
                    if(next ){
                        if((visibleItemCount + pastVisibleItems)>=totalItemCount){
                            next = false;
                            offset+=20;

                            array = queryUtils.getPokemons(getContext(), offset, pokeAdapter, new Query.onCallBack() {
                                @Override
                                public void onSuccsess(ArrayList<Poke> pokemon) {
                                    next = true;
                                    //  setView(pokemon);
                                }
                            });

                        }
                    }

                }
            }
        });

    }
    public void setOffset(){
        offset = 0;
    }


    @Override
    public void offsetOff() {
        offset = 0;
    }

    @Override
    public void iOff() {
        offset = 0;
    }
}
