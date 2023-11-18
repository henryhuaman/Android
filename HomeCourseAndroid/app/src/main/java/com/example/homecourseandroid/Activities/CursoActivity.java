package com.example.homecourseandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.homecourseandroid.Adapter.CursoAdapter;
import com.example.homecourseandroid.Api.CursoServiceAPI;
import com.example.homecourseandroid.Model.Curso;
import com.example.homecourseandroid.R;
import com.example.homecourseandroid.Util.ConexionREST;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CursoActivity extends AppCompatActivity {

    public CursoServiceAPI cursoServiceAPI;
    private RecyclerView recyclerView;
    private CursoAdapter cursoAdapter;
    private SearchView txtBuscar;
    private Spinner spnCate;
    private Button btnFiltrar;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curso);

        recyclerView = findViewById(R.id.ListRecycler);
        txtBuscar = findViewById(R.id.txtBuscar);
        spnCate = findViewById(R.id.spnCategoria);
        btnFiltrar = findViewById(R.id.btnFiltrar);
        toolbar = findViewById(R.id.toolbarCursos);
        setSupportActionBar(toolbar);

        cursoServiceAPI = ConexionREST.getConnection().create(CursoServiceAPI.class);
        cargarDatosDelAPI();
        txtBuscar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                cursoAdapter.filtradoSearch(newText);
                return false;
            }
        });

        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selected = spnCate.getSelectedItem().toString();
                cursoAdapter.filtradoSpn(selected);
            }
        });

    }

    void cargarDatosDelAPI(){
        Call<List<Curso>> call = cursoServiceAPI.listCursos();
        call.enqueue(new Callback<List<Curso>>() {
            @Override
            public void onResponse(Call<List<Curso>> call, Response<List<Curso>> response) {
                if(response.isSuccessful())
                {
                    List<Curso> lstCurso = response.body();

                    cursoAdapter = new CursoAdapter(lstCurso, CursoActivity.this);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(CursoActivity.this));
                    recyclerView.setAdapter(cursoAdapter);
                    //crea una lista con las categorias existentes
                    List<String> categoria = new ArrayList<>();
                    categoria.add("Sin filtro");
                    categoria.addAll(lstCurso.stream().map(s->s.getCategoria()).distinct().collect(Collectors.toList()));
                    //se crea un adaptador para q el spinner pueda usar la lista.
                    ArrayAdapter array = new ArrayAdapter(CursoActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item ,categoria);
                    //establecer el diseno del spinner
                    array.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //se establece el adaptador al spinner
                    spnCate.setAdapter(array);
                }else
                {
                    Toast.makeText(CursoActivity.this,"Error",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Curso>> call, Throwable t) {
                System.err.println("Error en la solicitud: " + t.getMessage());
                System.err.println("StackTrace: ");
                t.printStackTrace(); // Esto imprimirá la traza de la pila para obtener más detalles del error.
                Toast.makeText(CursoActivity.this,"Ocurrio un error",Toast.LENGTH_LONG).show();

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
        MenuItem itemInicioSesion = menu.findItem(R.id.itIniciarSesion);
        MenuItem itemRegistrarse = menu.findItem(R.id.itRegistrarse);
        View logoView = toolbar.getChildAt(0);
            ImageView logoImageView = (ImageView) logoView;
            // Agrega un OnClickListener al logo
            logoImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Crear un Intent para volver a la actividad específica
                    Intent intent = new Intent(CursoActivity.this, MainActivity.class);

                    // Configurar banderas para limpiar la pila de actividades y usar la instancia existente si está en la pila
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                    // Iniciar la actividad
                    startActivity(intent);
                }
            });

            // Configurar visibilidad basada en condiciones
            itemInicioSesion.setVisible(true);
            itemRegistrarse.setVisible(true);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.itIniciarSesion) {
            // Acción cuando se selecciona la opción de cursos
            return true;
        } else if (id == R.id.itRegistrarse) {
            // Acción cuando se selecciona la opción de profesores
            return true;
        } else if (id == R.id.itCarrito) {
            // Acción cuando se selecciona la opción del carrito
            Intent i = new Intent(CursoActivity.this,MainActivity.class);
            startActivity(i);
            return true;
        } else if (id ==R.id.itVolver) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}