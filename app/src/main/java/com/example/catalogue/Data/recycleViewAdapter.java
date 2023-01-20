package com.example.catalogue.Data;

import android.content.Context;

import com.example.catalogue.Model.StockMarket;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catalogue.R;

import java.util.List;

public class recycleViewAdapter extends RecyclerView.Adapter<recycleViewAdapter.ViewHolder> {
    private Context context;
    private List<StockMarket> stockList;

    public recycleViewAdapter(Context context, List<StockMarket> stock) {
        this.context = context;
        stockList = stock;
    }

    @NonNull
    @Override
    public recycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock, parent, false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull recycleViewAdapter.ViewHolder holder, int position) {
        StockMarket stocks = stockList.get(position);
        holder.txtclose.setText(stocks.getClose());
        holder.txtOpen.setText(stocks.getOpen());
        holder.txtDate.setText(stocks.getDate());
        holder.txtdifference.setText(stocks.getDifference());

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDate;
        TextView txtOpen;
        TextView txtclose;
        TextView txtdifference;


        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
        }
    }
}
