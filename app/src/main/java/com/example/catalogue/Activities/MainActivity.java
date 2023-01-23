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


        prefs Prefs = new prefs(MainActivity.this);
        String search = Prefs.getSearch();
        StockList = getStocks(search);

        StockRecycleViewAdapter = new recycleViewAdapter(this, StockList);
        recycleView.setAdapter(StockRecycleViewAdapter);
        StockRecycleViewAdapter.notifyDataSetChanged();

    }

    public List<StockMarket> getStocks(String searchTerm) {
        StockList.clear();

        String myurl = "https://quotient.p.rapidapi.com/equity/intraday?symbol=" + searchTerm + "&interval=1&from=2022-01-01 10:00&to=2022-12-25 10:00&adjust=false";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(myurl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                try {

                    for (int i = 0; i < 5; i++) {

                        JSONObject StocksObj  = response.getJSONObject(i);
                        StockMarket stockMarket = new StockMarket();
                        Log.d("String", "JLLO" + StocksObj);
                        stockMarket.setDate(StocksObj.getString("Date"));

                        stockMarket.setOpen(String.valueOf(StocksObj.getDouble("Open")));

                        stockMarket.setClose(String.valueOf(StocksObj.getDouble("Close")));

                        String Diff = "";
                        Double op = 0.0, clo = 0.0;

                        op = Double.parseDouble(String.valueOf(StocksObj.getDouble("Open")));


                        clo = Double.parseDouble(String.valueOf(StocksObj.getDouble("Close")));

                        Diff = String.valueOf(op - clo);

                        stockMarket.setDifference(Diff);
                        Log.d("JSON", "message=" +stockMarket.getDate() );
                        StockList.add(stockMarket);
                    }
                    StockRecycleViewAdapter.notifyDataSetChanged();

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ///bcbcbcbc
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("X-RapidAPI-Host", "quotient.p.rapidapi.com");
                params.put("X-RapidAPI-Key", "c7a4cd7806msh52cad41bef43255p11a663jsnfe210e545175");
                return params;

            }
        };
        requestQueue.add(jsonArrayRequest);
        return StockList;
    }
}