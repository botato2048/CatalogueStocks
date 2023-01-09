package com.example.catalogue.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.catalogue.Data.recycleViewAdapter;
import com.example.catalogue.Model.StockMarket;
import com.example.catalogue.R;
import com.example.catalogue.Util.prefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recycleView;
    private recycleViewAdapter StockRecycleViewAdapter;
    private List<StockMarket> StockList;
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);
        recycleView = findViewById(R.id.recycleView);
        recycleView.setHasFixedSize(true);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        StockList = new ArrayList<>();

        prefs prefs = new prefs(MainActivity.this);
        String search = prefs.getSearch();
        StockList = getStocks(search);

        StockRecycleViewAdapter = new recycleViewAdapter(this, StockList);
        recycleView.setAdapter(StockRecycleViewAdapter);
    }

    public List<StockMarket> getStocks(String searchTerm) {
        StockList.clear();

        String myurl = "https://quotient.p.rapidapi.com/equity/intraday?symbol=" + searchTerm + "&interval=1&from=2022-01-01 10:00&to=2022-12-25 10:00&adjust=false";
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(myurl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response)
            {
                Log.d("JSON","message=" + response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
     /*   JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(myurl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject JsObject =response.getJSONObject("Search");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        })
*/
return  StockList;
    }
}