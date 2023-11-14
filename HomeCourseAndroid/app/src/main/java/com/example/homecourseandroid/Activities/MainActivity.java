package com.example.homecourseandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

import com.example.homecourseandroid.R;

public class MainActivity extends AppCompatActivity {

    private Button btnProf;
    private Button btnCur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnProf = (Button) findViewById(R.id.btnProfesores);
        btnCur = (Button) findViewById(R.id.btnCursos);

        btnProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ProfActivity.class );
                startActivity(i);
            }
        });

    }


}