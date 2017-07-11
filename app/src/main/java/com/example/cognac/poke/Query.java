package com.example.cognac.poke;

import android.content.Context;

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

public class Query {

    ArrayList<Poke> array = new ArrayList<>();
    onCallBack onSuccess;

    public Query() {
    }

    public ArrayList<Poke> getPokemons(Context context, int offset, final PokeAdapter pokemonAdapter, final onCallBack help ){
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        //final ArrayList<String> array = new ArrayList<>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://pokeapi.co/api/v2/pokemon/?offset="+offset,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            for(int i = 0 ; i < jsonArray.length() ; i++){

                                JSONObject name = jsonArray.getJSONObject(i);

                                String Name = name.getString("name");
                                String url = name.getString("url");
                                System.out.println(" pokemon name: "+Name);
                                Poke poke = new Poke(Name,url);
                                array.add(poke);
                            }

                            help.onSuccsess(array);
                            if(pokemonAdapter!=null)
                                pokemonAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }

        });
        requestQueue.add(jsonObjectRequest);


        return array;

    }
    public interface onCallBack{
        void onSuccsess(ArrayList<Poke> pokemon);

    }


   /* public ArrayList<String> getPuke(Context context){
        ArrayList<String> puke = getPokemons(context, );

        return puke;
    }*/

}
