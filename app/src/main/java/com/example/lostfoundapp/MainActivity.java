package com.example.lostfoundapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppCompatButton create_new, show_all, show_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        create_new = findViewById(R.id.new_advert);
        show_all = findViewById(R.id.show_adverts);
        show_map = findViewById(R.id.show_map);

        create_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newAdvert = new Intent(MainActivity.this, NewItem.class);
                startActivity(newAdvert);
            }
        });

        show_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showItems = new Intent(MainActivity.this, ShowItems.class);
                startActivity(showItems);
            }
        });

        show_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showMap = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(showMap);
            }
        });
    }
}