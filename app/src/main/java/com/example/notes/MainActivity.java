package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> notes = new ArrayList<>();
    static ArrayAdapter<String> arrayAdapter;
    ListView noteList;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.addNote :
                Log.d("Add Note :", "onOptionsItemSelected: ");
                Intent intent = new Intent(MainActivity.this,noteActivity.class);
                intent.putExtra("no",-1);
                startActivity(intent);
                return true;
            default:return  false;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noteList = findViewById(R.id.listNote);
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        notes.clear();
        try{
            notes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("notes",ObjectSerializer.serialize(new ArrayList<String>())));
        }catch (Exception e){
            e.printStackTrace();
        }

        arrayAdapter =new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,notes);
        noteList.setAdapter(arrayAdapter);
        noteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,noteActivity.class);
                intent.putExtra("no",i);
                startActivity(intent);
            }
        });

        noteList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           final int index, long arg3) {

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.star_on)
                        .setTitle(" Delete Item! ")
                        .setMessage("Do you want to Continue!")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                notes.remove(index);
                                Toast.makeText(MainActivity.this,"successfullt Deleted :)",Toast.LENGTH_SHORT).show();
                                arrayAdapter.notifyDataSetChanged();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
                return true;
            }
        });
    }

}
