package com.example.homecourseandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.homecourseandroid.Adapter.CursoAdapter;
import com.example.homecourseandroid.Model.Curso;
import com.example.homecourseandroid.R;

import java.util.ArrayList;
import java.util.List;

public class ProfActivity extends AppCompatActivity {
    // Crear tres instancias de la clase Curso
    Curso curso1 = new Curso("1", "Curso de Programación", "Aprende a programar en Java", "Programación", "Profesor1", 99.99);
    Curso curso2 = new Curso("2", "Curso de Diseño Gráfico", "Introducción al diseño gráfico", "Diseño", "Profesor2", 79.99);
    Curso curso3 = new Curso("3", "Curso de Inglés", "Mejora tus habilidades en inglés", "Idiomas", "Profesor3", 49.99);

    // Crear una lista y agregar los cursos a la lista
    List<Curso> listaCursos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof);
        listaCursos.add(curso1);
        listaCursos.add(curso2);
        listaCursos.add(curso3);
        listaCursos.add(curso1);
        listaCursos.add(curso2);
        listaCursos.add(curso3);
        listaCursos.add(curso1);
        listaCursos.add(curso2);
        listaCursos.add(curso3);


        CursoAdapter cursoAdapter = new CursoAdapter(listaCursos, ProfActivity.this);
        RecyclerView recyclerView = findViewById(R.id.ListRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cursoAdapter);
    }
}