package com.example.lostfoundapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder>{

    ArrayList<ItemModel> listItems;

    public ItemsAdapter(ArrayList<ItemModel> listItems) {
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public ItemsAdapter.ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ItemsViewHolder((view));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.ItemsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.item_name.setText(listItems.get(position).name);
        holder.item_description.setText(listItems.get(position).description);
        holder.location.setText(listItems.get(position).location);
        holder.date.setText(listItems.get(position).date);
        holder.status.setText(listItems.get(position).status.toUpperCase());
        //holder.status.setTextColor(getTextColor(listItems.get(position).status));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();

                Intent itemIntent = new Intent(context, SingleItem.class);
                itemIntent.putExtra("id", String.valueOf(listItems.get(position).id));
                context.startActivity(itemIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public int getTextColor(String status) {
        switch (status) {
            case "lost":
                return R.color.red;
            case "found":
                return R.color.green;
            default:
                return R.color.black;
        }
    }

    class ItemsViewHolder extends RecyclerView.ViewHolder {
        LinearLayout itemLayout;
        TextView item_name, item_description, location, date, status;
        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.itemName);
            item_description = itemView.findViewById(R.id.item_description);
            location = itemView.findViewById(R.id.location);
            date = itemView.findViewById(R.id.date);
            status = itemView.findViewById(R.id.itemStatus);
            itemLayout = itemView.findViewById(R.id.item_layout);
        }
    }
}
