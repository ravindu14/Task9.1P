package com.example.lostfoundapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SingleItem extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    TextView itemName, itemDescription, itemLocation, itemDate, title;

    AppCompatButton remove_button;
    ItemModel item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item);

        final int id = Integer.parseInt(getIntent().getStringExtra("id"));


        title = findViewById(R.id.title);
        itemName = findViewById(R.id.itemName);
        itemDescription = findViewById(R.id.itemDescription);
        itemLocation = findViewById(R.id.itemLocation);
        itemDate = findViewById(R.id.itemDate);
        remove_button = findViewById(R.id.remove_button);

        databaseHelper = new DatabaseHelper(getApplicationContext());

        item = databaseHelper.getItem(id);

        title.setText(item.status.toUpperCase()+ " ITEM");
        itemName.setText(item.name);
        itemDescription.setText(item.description);
        itemLocation.setText(item.location);
        itemDate.setText(item.date);

        remove_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    databaseHelper.removeItem(item.id);

                    Toast.makeText(SingleItem.this, "Item removed successfully", Toast.LENGTH_SHORT).show();

                    Intent showItems = new Intent(SingleItem.this, ShowItems.class);
                    startActivity(showItems);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
    }
}