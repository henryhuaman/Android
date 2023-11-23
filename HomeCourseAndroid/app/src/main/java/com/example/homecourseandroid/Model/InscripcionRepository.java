package com.example.homecourseandroid.Model;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.homecourseandroid.Api.InscripcionServiceAPI;
import com.example.homecourseandroid.Util.ConexionREST;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InscripcionRepository {
    InscripcionServiceAPI inscripcionServiceAPI = ConexionREST.getConnection().create(InscripcionServiceAPI.class);
    Context context;

    public InscripcionRepository(Context context) {
        this.context = context;
    }

    public void agregar(Inscripcion inscripcion){
        Call<Inscripcion> callObj = inscripcionServiceAPI.addInscripcion(inscripcion);
        callObj.enqueue(new Callback<Inscripcion>() {
            @Override
            public void onResponse(Call<Inscripcion> call, Response<Inscripcion> response) {
                if (response.errorBody() != null) {
                    try {
                        Log.e("TuFragmento", "Error del servidor: " + response.errorBody().string());
                    } catch (IOException e) {
                        Log.e("TuFragmento", "Error al obtener el cuerpo de error: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<Inscripcion> call, Throwable t) {
                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public Inscripcion getInscripcion(String idCur, String idUsu){
        List<Inscripcion> lstIns = getInscripciones();
        return lstIns.stream().filter(ins -> ins.getCursoId().equals(idCur) && ins.getUsuarioId().equals(idUsu)).findAny().orElse(null);
    }
    public List<Inscripcion> getInscripciones(){
        List<Inscripcion> lst = new ArrayList<>();
        Call<List<Inscripcion>> call =inscripcionServiceAPI.listInscripciones();
        call.enqueue(new Callback<List<Inscripcion>>() {
            @Override
            public void onResponse(Call<List<Inscripcion>> call, Response<List<Inscripcion>> response) {
                List<Inscripcion> lstApi = response.body();
                if(!lstApi.isEmpty()){
                    for (Inscripcion b:lstApi) {
                        lst.add(b);
                    }
                }else{

                }

            }
            @Override
            public void onFailure(Call<List<Inscripcion>> call, Throwable t) {
                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
            }
        });
        return lst;
    }
    public String generarCodigo(List<Inscripcion> lstInscripciones) {
        String codigo = "";
        int quant = 0;

        if (lstInscripciones != null && !lstInscripciones.isEmpty()) {
            // Obtener el número máximo de CodOpe
            int numero = lstInscripciones.stream()
                    .map(a -> Integer.parseInt(a.getCodOpe().replaceAll("\\D", "")))
                    .distinct()
                    .max(Integer::compare)
                    .orElse(0);

            quant = numero + 1;
        }

        // Formatear el número con ceros a la izquierda
        String formattedNumber = String.format("%03d", quant);
        codigo = "IN" + formattedNumber;

        return codigo;
    }


    public int teDoy(String cod){
        return Integer.parseInt(cod.replaceAll("\\D", ""));
    }

}
