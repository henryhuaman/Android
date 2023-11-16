package com.example.homecourseandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.homecourseandroid.Model.Curso;
import com.example.homecourseandroid.R;

public class DetallesCursoActivity extends AppCompatActivity {

    private TextView nomCur;
    private TextView desCur;
    private TextView proCur;
    private Button btnVolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_curso);
        Intent intent = getIntent();

        Curso curso = (Curso) intent.getSerializableExtra("Curso");

        nomCur = findViewById(R.id.vtNombre);
        desCur = findViewById(R.id.vtDescripcion);
        proCur = findViewById(R.id.vtProfesor);
        btnVolver = findViewById(R.id.btnVolver);

        nomCur.append(curso.getNombre());
        desCur.append(curso.getDescripcion());
        proCur.append(curso.getProfesorId());

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}