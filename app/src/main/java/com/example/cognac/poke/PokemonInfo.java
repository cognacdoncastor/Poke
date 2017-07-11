package com.example.cognac.poke;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Cognac on 7/11/2017.
 */

public class PokemonInfo extends Fragment {

    TextView attack, defense, hp, specialAttack, specialDefense, speed, name, desc,weight,height,habitat,catchRate, ability;
    ImageView image;
    Button skin;
    String im, name_passed;
    int num;
    Query queryUtils;
    Inter inter;
    Context cont;




    public PokemonInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.info, container, false);
        skin = (Button)view.findViewById(R.id.skin);
        weight = (TextView)view.findViewById(R.id.weight);
        height = (TextView)view.findViewById(R.id.height);
        habitat = (TextView)view.findViewById(R.id.habitat);
        catchRate = (TextView)view.findViewById(R.id.catchRate);
        desc = (TextView)view.findViewById(R.id.desc);
        name = (TextView)view.findViewById(R.id.name);
        image = (ImageView)view.findViewById(R.id.image);
        attack = (TextView)view.findViewById(R.id.attack);
        defense = (TextView)view.findViewById(R.id.defense);
        hp = (TextView)view.findViewById(R.id.hp);
        specialAttack = (TextView)view.findViewById(R.id.specialAttack);
        specialDefense = (TextView)view.findViewById(R.id.specialDefense);
        speed = (TextView)view.findViewById(R.id.speed);
        ability = (TextView)view.findViewById(R.id.ability);


        inter = (Inter)getTargetFragment();

        cont = view.getContext();


        //    off();


        queryUtils = new Query();


        skin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                FragmentManager fr = getFragmentManager();
                bundle.putInt("number",num);
                bundle.putString("name",name_passed);
                Skins skinsFragment = new Skins();

                skinsFragment.setArguments(bundle);
                skinsFragment.show(fr,"how");

            }
        });


        if(getArguments()!=null){
            im = getArguments().getString("image");
            num = getArguments().getInt("number");
            name_passed = getArguments().getString("name");

            Glide.with(getContext()).load(im).centerCrop().crossFade().into(image);
            name.setText(name_passed);

        }


        getAbilities(getContext());



        return view;
    }


    private void getAbilities(Context context) {
        final RequestQueue request;
//        inter.offsetOff();

        request = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.GET, "http://pokeapi.co/api/v2/pokemon/" + num + "/", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("stats");


                    JSONArray jsonArray1 = response.getJSONArray("abilities");
                    for(int i = 0; i < jsonArray1.length(); i++){
                        JSONObject abilitties = jsonArray1.getJSONObject(i);
                        JSONObject acess = abilitties.getJSONObject("ability");
                        String abilityName = acess.getString("name");
                        if(jsonArray1.length()-1 == i ){
                            ability.append(abilityName);
                        }else
                            ability.append(abilityName+", ");

                    }

                    int hp=0, speed=0, specDef=0, specAtt=0, attack=0, def=0, weight=0, height=0;
                    height = response.getInt("height");
                    weight =  response.getInt("weight");


                    for(int i = 0; i < jsonArray.length();i++){
                        JSONObject stats = jsonArray.getJSONObject(i);


                        JSONObject stat = stats.getJSONObject("stat");
                        String nameOfStats = stat.getString("name");

                        int quant = stats.getInt("base_stat");
                        System.out.println("name stats = "+nameOfStats+" quant = "+quant);

                        switch (nameOfStats){
                            case "hp": hp = quant; break;
                            case "speed": speed = quant; break;
                            case "attack": attack = quant; break;
                            case "defense": def = quant; break;
                            case "special-attack": specAtt = quant; break;
                            case "special-defense": specDef = quant; break;
                        }

                    }

                    JsonObjectRequest jsonobjreq3 = new JsonObjectRequest(Request.Method.GET, "http://pokeapi.co/api/v2/pokemon-species/" + num+"/", new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                int cap = response.getInt("capture_rate");
                                JSONObject habitats = response.getJSONObject("habitat");
                                String hab = habitats.getString("name");

                                habitat.setText(hab);
                                catchRate.setText(cap+"");

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

                    request.add(jsonobjreq3);

                    JsonObjectRequest jsonobjreq2 = new JsonObjectRequest(Request.Method.GET, "https://pokeapi.co/api/v2/characteristic/" + num + "/", new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            String descr;
                            try {
                                JSONArray dess = response.getJSONArray("descriptions");

                                for(int i = 0; i < dess.length(); i++){
                                    JSONObject var = dess.getJSONObject(i);

                                    JSONObject lang = var.getJSONObject("language");
                                    String langg = lang.getString("name");
                                    if(langg.equals("en")){
                                        descr = var.getString("description");
                                        desc.setText(descr);

                                    }
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
                    request.add(jsonobjreq2);

                    PokemonStats pokemoStats = new PokemonStats(name_passed,num,hp,def,attack,speed,specAtt,specDef, height, weight);


                    setUI(pokemoStats);

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
        request.add(jsonObjectReq);

    }

    public void setUI(PokemonStats pokemonstats){
        name.setText(pokemonstats.getName()+"");
        attack.setText(pokemonstats.getAttack()+"");
        defense.setText(pokemonstats.getDefense()+"");
        specialAttack.setText(pokemonstats.getSpecialAttack()+"");
        specialDefense.setText(pokemonstats.getSpecialDefense()+"");
        hp.setText(pokemonstats.getHp()+"");
        speed.setText(pokemonstats.getSpeed()+"");
        height.setText(pokemonstats.getHeight()+"");
        weight.setText(pokemonstats.getWeight()+"");

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.inter = (Inter)this.cont;
    }

    public static interface Inter{
        public void offsetOff();
    }

}
