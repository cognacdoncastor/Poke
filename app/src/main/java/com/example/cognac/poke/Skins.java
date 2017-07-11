package com.example.cognac.poke;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Cognac on 7/12/2017.
 */

public class Skins extends DialogFragment {
    ImageView front, back, frontShiny, backShiny;

    public Skins() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        //  dialog.getWindow().setBackgroundDrawableResource();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.skin, container, false);
        front = (ImageView)view.findViewById(R.id.front);
        back = (ImageView)view.findViewById(R.id.back);
        frontShiny = (ImageView)view.findViewById(R.id.shinyFront);
        backShiny = (ImageView)view.findViewById(R.id.shinyBack);
        int num=0;
        String name;

        setStyle(STYLE_NO_TITLE,0);

        if(getArguments()!=null){
            num = getArguments().getInt("number");
            name = getArguments().getString("name");
            getDialog().hide();
            getDialog().dismiss();
            insertImages(num);
        }


        return view;
    }
    public void insertImages(int num){
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://pokeapi.co/api/v2/pokemon-form/" + num + "/", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject sprites = response.getJSONObject("sprites");
                    String frontS = sprites.getString("front_default");
                    String backS = sprites.getString("back_default");
                    String front_shinyS = sprites.getString("front_shiny");
                    String back_shinyS = sprites.getString("back_shiny");

                    Glide.with(getContext()).load(frontS).into(front);
                    Glide.with(getContext()).load(backS).into(back);
                    Glide.with(getContext()).load(front_shinyS).into(frontShiny);
                    Glide.with(getContext()).load(back_shinyS).into(backShiny);

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

        requestQueue.add(jsonObjectRequest);
    }

}
