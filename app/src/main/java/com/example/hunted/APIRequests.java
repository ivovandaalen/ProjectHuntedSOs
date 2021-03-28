package com.example.hunted;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class APIRequests {

    RequestQueue queue;
    private Context context;
    String url = "https://apihuntedsos.herokuapp.com/";
    String distances;

    public APIRequests(Context context) {
        this.context = context;
        queue = Volley.newRequestQueue(context);
    }

    //POST: Maak nieuwe speler aan
    public JSONObject MakeNewPlayer(String name, int role){
        final JSONObject player = new JSONObject();
        try {
            player.put("name", name);
            player.put("role", role);
            player.put("arrested", false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String newPlayerURL = url + "player/";
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, newPlayerURL, player,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context.getApplicationContext(), name + " is toegevoegd als speler", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
        return player;
    }

    //PUT: Stuur locatie van player
    public void SendPlayerLocation(JSONObject location, String id) {
        String setlocURL = url + "player/location/" + id;
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.PUT, setlocURL, location,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                                textView.setText("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
    }

    //GET: Alle spelers
    public JSONArray GetAllPlayers() {
        String getPlayersURL = url + "player/";
        JSONArray playerList = new JSONArray();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for(int i = 0; i < response.length(); i++){
                        JSONObject object = response.getJSONObject(i);
                        playerList.put(i, object);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);
        return playerList;
    }

    //GET: Alle afstanden van de opgegeven speler tot alle andere spelers
    public String GetDistancesFromOnePlayer(String id) {
        String getDistURL = url + "player/distances/" + id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, getDistURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        distances = response;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
        return distances;
    }


//return player location
    public JSONObject getPlayerLocation(JSONObject location) {
        return location;
    }

}
