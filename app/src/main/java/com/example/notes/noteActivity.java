package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class noteActivity extends AppCompatActivity {
    EditText editNote;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        editNote = findViewById(R.id.editNote);
        button = findViewById(R.id.saveButton);
        final SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        final int no = getIntent().getIntExtra("no",-1);

        if(no == -1) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.notes.add(editNote.getText().toString());
                    Log.d("Seee", "onCreate: " + editNote.getText().toString());
                    MainActivity.arrayAdapter.notifyDataSetChanged();
                    try {
                        sharedPreferences.edit().putString("notes", ObjectSerializer.serialize(MainActivity.notes)).apply();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finish();
                }
            });
        }else{
            editNote.setText(MainActivity.notes.get(no));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.notes.remove(no);
                    MainActivity.notes.add(editNote.getText().toString());
                    Log.d("Seee", "onCreate: " + editNote.getText().toString());
                    MainActivity.arrayAdapter.notifyDataSetChanged();
                    try {
                        sharedPreferences.edit().putString("notes", ObjectSerializer.serialize(MainActivity.notes)).apply();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finish();
                }
            });

        }

 /*
        This can also be used in place of Button...

        editNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                MainActivity.notes.set(no,String.valueOf(charSequence));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
*/
    }
}
