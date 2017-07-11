package com.example.cognac.poke;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Cognac on 7/11/2017.
 */

public class ItemEffects extends Fragment {
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    RecyclerView listView;
    String name, url, imgUrl, effect;
    int num;
    boolean next = true;
    int offset = 0;
    ArrayList<Item> itemModels;

    public ItemEffects() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.item, container, false);

        listView = (RecyclerView)view.findViewById(R.id.recyclerView);

        itemModels = new ArrayList<>();
        getItem(view,offset);
        setView(view);
        next = true;
        Toast.makeText(getContext(), "Loading...", Toast.LENGTH_LONG).show();
        return view;
    }

    private void getItem(final View view, int offset) {
        final RequestQueue request;
        request = Volley.newRequestQueue(getContext());




        JsonObjectRequest itemRequest = new JsonObjectRequest(Request.Method.GET, "http://pokeapi.co/api/v2/item/?offset="+offset, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("results");

                    for(int i=0; i < jsonArray.length();i++){
                        JSONObject item = jsonArray.getJSONObject(i);
                        name = item.getString("name");
                        url = item.getString("url");
                        String []urlParties = url.split("/");
                        num = Integer.parseInt(urlParties[urlParties.length - 1]);
                        imgUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/"+name+".png";
                        final Item itemModel = new Item(name,imgUrl,url);

                        final String[] effects = new String[1];
                        JsonObjectRequest itemInfoRequest = new JsonObjectRequest(Request.Method.GET, "http://pokeapi.co/api/v2/item/" + num + "/", new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {

                                    JSONArray effect_entries = response.getJSONArray("effect_entries");
                                    for(int i = 0; i < effect_entries.length(); i++){
                                        JSONObject effectVar = effect_entries.getJSONObject(i);

                                        effects[0] = effectVar.getString("effect");
                                        System.out.println("effect = "+effects[0]);
                                        getEffect(effect);
                                        itemModel.setEffect(effects[0]);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }
                        );
                        itemModels.add(itemModel);
                        request.add(itemInfoRequest);
                        setView(view);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        request.add(itemRequest);
    }

    public String getEffect(String effect){
        return effect;
    }



    public void setView(final View view){
        final LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        listView.setLayoutManager(layoutManager);
        adapter = new ItemAdapter(itemModels,view.getContext());
        listView.setAdapter(adapter);

        listView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (next) {
                    if (dy > 0) {
                        int visibleItemCount = layoutManager.getChildCount();
                        int totalitemCount = layoutManager.getItemCount();
                        int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                        if (visibleItemCount +pastVisibleItems >= totalitemCount) {
                            next= false;
                            offset += 20;
                            getItem(view, offset);
                            next= true;
                            Toast.makeText(getContext(), "End", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }
        });

    }

}
