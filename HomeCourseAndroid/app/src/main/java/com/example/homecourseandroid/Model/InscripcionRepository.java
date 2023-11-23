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

}
