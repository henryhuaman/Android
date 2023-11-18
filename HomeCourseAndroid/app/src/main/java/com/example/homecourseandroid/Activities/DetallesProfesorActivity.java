package com.example.homecourseandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homecourseandroid.Api.CursoServiceAPI;
import com.example.homecourseandroid.Api.ProfesorServiceAPI;
import com.example.homecourseandroid.Model.Curso;
import com.example.homecourseandroid.Model.Profesor;
import com.example.homecourseandroid.R;
import com.example.homecourseandroid.Util.ConexionREST;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetallesProfesorActivity extends AppCompatActivity {
    private TextView nomProf, corProf, telfProf,curProf ;
    private Button btnVolver;
    private CursoServiceAPI cursoServiceAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_profesor);
        cursoServiceAPI = ConexionREST.getConnection().create(CursoServiceAPI.class);

        Intent i = getIntent();
        Profesor profesor = (Profesor) i.getSerializableExtra("Profesor");

        nomProf = findViewById(R.id.vtNombreProf);
        corProf = findViewById(R.id.vtCorrProfesor);
        telfProf = findViewById(R.id.vtTelfProf);
        btnVolver = findViewById(R.id.btnVolverProf);
        curProf = findViewById(R.id.vtCurProf);

        nomProf.append(profesor.getNombre());
        corProf.append(profesor.getCorreo());
        telfProf.append(profesor.getTelefono());

        generarCursosDeProfesor(profesor.getId());

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    void generarCursosDeProfesor(String id){
        Call<List<Curso>> call = cursoServiceAPI.listCursos();
        call.enqueue(new Callback<List<Curso>>() {
            @Override
            public void onResponse(Call<List<Curso>> call, Response<List<Curso>> response) {
                if(response.isSuccessful())
                {
                    List<Curso> lstProf = response.body();
                    List<Curso> lstCurProf = lstProf.stream().filter(curso -> curso.getProfesorId().equals(id)).collect(Collectors.toList());
                    String aux = "";
                    for (Curso a : lstCurProf) {
                        aux+="- "+a.getNombre()+"\n";
                    }

                    /*StringBuilder aux = new StringBuilder();
                    lstCurProf.forEach(curso -> aux.append("- "+curso.getNombre()+"\n"));*/
                    curProf.setText(aux);
                }else
                {
                    Toast.makeText(null,"Error",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Curso   >> call, Throwable t) {
                System.err.println("Error en la solicitud: " + t.getMessage());
                System.err.println("StackTrace: ");
                t.printStackTrace(); // Esto imprimirá la traza de la pila para obtener más detalles del error.
                Toast.makeText(DetallesProfesorActivity.this,"Ocurrio un error",Toast.LENGTH_LONG).show();

            }
        });
    }

}