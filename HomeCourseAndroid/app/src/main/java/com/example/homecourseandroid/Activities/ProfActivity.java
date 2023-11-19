package com.example.homecourseandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.homecourseandroid.Adapter.CursoAdapter;
import com.example.homecourseandroid.Adapter.ProfesorAdapter;
import com.example.homecourseandroid.Api.ProfesorServiceAPI;
import com.example.homecourseandroid.Model.Curso;
import com.example.homecourseandroid.Model.Profesor;
import com.example.homecourseandroid.R;
import com.example.homecourseandroid.Util.ConexionREST;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfActivity extends AppCompatActivity{

    public ProfesorServiceAPI profesorServiceAPI;
    private RecyclerView recyclerViewProf;
    private ProfesorAdapter profesorAdapter;
    private Toolbar toolbar;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof);

        recyclerViewProf = findViewById(R.id.ListRecyclerProf);
        toolbar = findViewById(R.id.toolbarProf);
        setSupportActionBar(toolbar);
        sessionManager = SessionManager.getInstance(this);

        profesorServiceAPI = ConexionREST.getConnection().create(ProfesorServiceAPI.class);
        cargarDatosDelAPI();

    }

    void cargarDatosDelAPI(){
        Call<List<Profesor>> call = profesorServiceAPI.listProfesores();
        call.enqueue(new Callback<List<Profesor>>() {
            @Override
            public void onResponse(Call<List<Profesor>> call, Response<List<Profesor>> response) {
                if(response.isSuccessful())
                {
                    List<Profesor> lstProf = response.body();

                    profesorAdapter = new ProfesorAdapter(lstProf, ProfActivity.this);
                    recyclerViewProf.setHasFixedSize(true);
                    recyclerViewProf.setLayoutManager(new LinearLayoutManager(ProfActivity.this));
                    recyclerViewProf.setAdapter(profesorAdapter);
                }else
                {
                    Toast.makeText(null,"Error",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Profesor>> call, Throwable t) {
                System.err.println("Error en la solicitud: " + t.getMessage());
                System.err.println("StackTrace: ");
                t.printStackTrace(); // Esto imprimirá la traza de la pila para obtener más detalles del error.
                Toast.makeText(ProfActivity.this,"Ocurrio un error",Toast.LENGTH_LONG).show();

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
        MenuItem itemIniciarSesion = menu.findItem(R.id.itIniciarSesion);
        MenuItem itemRegistrarse = menu.findItem(R.id.itRegistrarse);
        MenuItem itemTusCursos = menu.findItem(R.id.itTusCursos);
        MenuItem itemProfesores = menu.findItem(R.id.itProfesores);
        MenuItem itemCursos = menu.findItem(R.id.itCursos);

        if(sessionManager.isLoggedIn()){
            itemTusCursos.setVisible(false);
            itemRegistrarse.setVisible(false);
            itemCursos.setVisible(false);
            itemProfesores.setVisible(false);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.itIniciarSesion) {
            // Acción cuando se selecciona la opción de cursos
            startActivity(new Intent(getApplicationContext(),IniciarActivity.class));
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