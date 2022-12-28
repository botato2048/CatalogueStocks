package com.example.catalogue.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.RequestQueue;

import com.android.volley.toolbox.Volley;
import com.example.catalogue.Data.recycleViewAdapter;
import com.example.catalogue.Model.StockMarket;
import com.example.catalogue.R;
import com.example.catalogue.Util.prefs;

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
         
    }
}