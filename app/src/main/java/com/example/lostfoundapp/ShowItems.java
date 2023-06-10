package com.example.lostfoundapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ShowItems extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    RecyclerView itemsView;
    ArrayList<ItemModel> list;
    AppCompatButton back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_items);

        back_button = findViewById(R.id.back_button);

        databaseHelper = new DatabaseHelper(getApplicationContext());

        list = databaseHelper.getAllItems();

        itemsView = findViewById(R.id.items_view);

        LinearLayoutManager linearLayoutNewsManager = new LinearLayoutManager(this);
        linearLayoutNewsManager.setOrientation(RecyclerView.VERTICAL);
        itemsView.setLayoutManager(linearLayoutNewsManager);

        itemsView.setHasFixedSize(true);

        System.out.println(list);

        itemsView.setAdapter(new ItemsAdapter((list)));


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainMenu = new Intent(ShowItems.this, MainActivity.class);
                startActivity(mainMenu);
            }
        });
    }
}