package com.example.homecourseandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import com.example.homecourseandroid.R;

public class MainActivity extends AppCompatActivity {

    private Button btnProf;
    private Button btnCur;
    Toolbar toolbar;

    private boolean mostrarCursos = true;
    private boolean mostrarProfesores = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnProf = (Button) findViewById(R.id.btnProfesores);
        btnCur = (Button) findViewById(R.id.btnCursos);
        toolbar = findViewById(R.id.toolbarr);
        setSupportActionBar(toolbar);

        //ToolBarr.setupToolbar(this, toolbar, "Título de la Actividad", true);
        btnCur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CursoActivity.class );
                startActivity(i);
            }
        });

        btnProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfActivity.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navbar, menu);
        configurarVisibilidadMenu(menu);
        return true;
    }

    private void configurarVisibilidadMenu(Menu menu) {
        MenuItem itemCursos = menu.findItem(R.id.itIniciarSesion);
        MenuItem itemProfesores = menu.findItem(R.id.itRegistrarse);

        // Configurar visibilidad basada en condiciones
        itemCursos.setVisible(mostrarCursos);
        itemProfesores.setVisible(mostrarProfesores);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.itIniciarSesion) {
            // Acción cuando se selecciona la opción de cursos
            startActivity(new Intent(MainActivity.this,IniciarActivity.class));
            return true;
        } else if (id == R.id.itRegistrarse) {
            // Acción cuando se selecciona la opción de profesores
            return true;
        } else if (id == R.id.itCarrito) {
            // Acción cuando se selecciona la opción del carrito

            return true;
        } else if (id ==R.id.itVolver) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}