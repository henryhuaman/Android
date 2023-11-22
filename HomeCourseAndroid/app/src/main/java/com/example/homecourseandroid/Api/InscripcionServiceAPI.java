package com.example.homecourseandroid.Api;

import com.example.homecourseandroid.Model.Inscripcion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface InscripcionServiceAPI {

    @GET("inscripcion")
    public abstract Call<List<Inscripcion>> listInscripciones();

    @POST("inscripcion/agregar")
    public abstract Call<Inscripcion> addInscripcion(@Body Inscripcion obj);

    @PUT("inscripcion/modificar")
    public abstract Call<Inscripcion> modifyInscripcion(@Body Inscripcion obj);

    @DELETE("inscripcion/eliminar/{idInscripcion}")
    public abstract Call<Inscripcion> removeInscripcion(@Path("idInscripcion") String id);

}
