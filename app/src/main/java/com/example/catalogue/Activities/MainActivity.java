package com.example.catalogue.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
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
//jdjdjd

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        String myurl = "https://quotient.p.rapidapi.com/equity/intraday?symbol=" + searchTerm + "&interval=60&from=2022-12-01 10:00&to=2022-12-25 10:00&adjust=false";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(myurl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("JSON", "message=" + response);

                for (int a = 0; a < response.length(); a++) {
                    try {
                        JSONArray StockArray = new JSONArray();


                        StockArray = response.getJSONArray(a);

                        for (int i = 0; i < StockArray.length(); i++) {

                            JSONObject StocksObj = new JSONObject();

                            StocksObj = StockArray.getJSONObject(i);

                            StockMarket stockMarket = new StockMarket();

                         //   stockMarket.setDate(StocksObj.getString("Date"));

                          //  stockMarket.setOpen(StocksObj.getString("Open"));

                          //  stockMarket.setClose(StocksObj.getString("Close"));

                            String Diff = "";
                            int op = 0, clo = 0;

                            op = Integer.parseInt(StocksObj.getString("Open"));


                            clo = Integer.parseInt(StocksObj.getString("Close"));

                            Diff = String.valueOf(op - clo);

                        //    stockMarket.setDifference(Diff);

                            StockList.add(stockMarket);
                        }
                        StockRecycleViewAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("X-RapidAPI-Host", "quotient.p.rapidapi.com");
                params.put("X-RapidAPI-Key", "ea24bc548bmsh093922019c0de63p100fe6jsn138798df0854");
                return params;

            }
        };
        requestQueue.add(jsonArrayRequest);
        return StockList;
    }
}
