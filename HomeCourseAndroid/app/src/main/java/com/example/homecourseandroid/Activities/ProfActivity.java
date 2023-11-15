package com.example.homecourseandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.homecourseandroid.Adapter.CursoAdapter;
import com.example.homecourseandroid.Api.ServiceAPI;
import com.example.homecourseandroid.Model.Curso;
import com.example.homecourseandroid.R;
import com.example.homecourseandroid.Util.ConexionREST;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfActivity extends AppCompatActivity{

    public ServiceAPI serviceAPI;
    private RecyclerView recyclerView;
    private CursoAdapter cursoAdapter;
    private SearchView txtBuscar;
    private Spinner spnCate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof);
        recyclerView = findViewById(R.id.ListRecycler);
        txtBuscar = findViewById(R.id.txtBuscar);
        spnCate = findViewById(R.id.spnCategoria);
        //card = findViewById()
        serviceAPI = ConexionREST.getConnection().create(ServiceAPI.class);
        cargarDatosDelAPI();
        txtBuscar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                cursoAdapter.filtrado(newText);
                return false;
            }
        });


    }

    void cargarDatosDelAPI(){
        Call<List<Curso>> call = serviceAPI.listCursos();
        call.enqueue(new Callback<List<Curso>>() {
            @Override
            public void onResponse(Call<List<Curso>> call, Response<List<Curso>> response) {
                if(response.isSuccessful())
                {
                    List<Curso> lstCurso = response.body();
                    List<String> categoria = lstCurso.stream().map(s->s.getCategoria()).distinct().collect(Collectors.toList());
                    ArrayAdapter array = new ArrayAdapter(ProfActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item ,categoria);

                    cursoAdapter = new CursoAdapter(lstCurso, ProfActivity.this);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ProfActivity.this));
                    recyclerView.setAdapter(cursoAdapter);
                    array.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnCate.setAdapter(array);
                }else
                {
                    Toast.makeText(null,"Error",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Curso>> call, Throwable t) {
                System.err.println("Error en la solicitud: " + t.getMessage());
                System.err.println("StackTrace: ");
                t.printStackTrace(); // Esto imprimirá la traza de la pila para obtener más detalles del error.
                Toast.makeText(ProfActivity.this,"Ocurrio un error",Toast.LENGTH_LONG).show();

            }
        });
    }


}