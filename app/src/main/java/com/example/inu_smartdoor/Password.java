package com.example.inu_smartdoor;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Password extends MainActivity {

    Button btn01;
    Button btn02;
    Button btn03;
    Button btn04;
    Button btn05;
    Button btn06;
    Button btn07;
    Button btn08;
    Button btn09;
    Button btn00;
    Button remove;
    Button enterpw;
    String anspw = "";
    String BTpass;
    EditText editText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password);

        btn01 = findViewById(R.id.button01);
        btn02 = findViewById(R.id.button02);
        btn03 = findViewById(R.id.button03);
        btn04 = findViewById(R.id.button04);
        btn05 = findViewById(R.id.button05);
        btn06 = findViewById(R.id.button06);
        btn07 = findViewById(R.id.button07);
        btn08 = findViewById(R.id.button08);
        btn09 = findViewById(R.id.button09);
        btn00 = findViewById(R.id.button00);
        remove = findViewById(R.id.buttonrm);
        enterpw = findViewById(R.id.buttonet);
        editText = findViewById(R.id.editText);
        getPreferences();
        settListener();
    }
    private void getPreferences() {
        SharedPreferences mPref = getSharedPreferences("pref", MODE_PRIVATE);
        BTpass = mPref.getString("pass", "0000");
    }
    public void settListener() {
        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anspw = anspw + "1";
                editText.setText(anspw);
                editText.setSelection(editText.length());
            }
        });
        btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anspw = anspw + "2";
                editText.setText(anspw);
                editText.setSelection(editText.length());
            }
        });
        btn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anspw = anspw + "3";
                editText.setText(anspw);
                editText.setSelection(editText.length());
            }
        });
        btn04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anspw = anspw + "4";
                editText.setText(anspw);
                editText.setSelection(editText.length());
            }
        });
        btn05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anspw = anspw + "5";
                editText.setText(anspw);
                editText.setSelection(editText.length());
            }
        });
        btn06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anspw = anspw + "6";
                editText.setText(anspw);
                editText.setSelection(editText.length());
            }
        });
        btn07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anspw = anspw + "7";
                editText.setText(anspw);
                editText.setSelection(editText.length());
            }
        });
        btn08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anspw = anspw + "8";
                editText.setText(anspw);
                editText.setSelection(editText.length());
            }
        });
        btn09.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anspw = anspw + "9";
                editText.setText(anspw);
                editText.setSelection(editText.length());
            }
        });
        btn00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anspw = anspw + "0";
                editText.setText(anspw);
                editText.setSelection(editText.length());
            }
        });
        enterpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BTpass.equals(anspw)) {
                    Intent intent = new Intent(Password.this, webcam.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "틀렸습니다.",
                            Toast.LENGTH_SHORT).show();
                }
                anspw = "";
                editText.setText(anspw);
                editText.setSelection(editText.length());
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anspw = "";
                editText.setText(anspw);
                editText.setSelection(editText.length());
            }
        });
    }
}


