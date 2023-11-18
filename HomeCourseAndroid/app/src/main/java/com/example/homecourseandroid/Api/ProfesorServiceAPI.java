package com.example.homecourseandroid.Api;

import com.example.homecourseandroid.Model.Curso;
import com.example.homecourseandroid.Model.Profesor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProfesorServiceAPI {

    @GET("profesor")
    public abstract Call<List<Profesor>> listProfesores();
    @POST("profesor/agregar")
    public abstract Call<Profesor> addProfesor(@Body Profesor obj);
    @PUT("/profesor/modificar")
    public abstract  Call<Profesor> modifyProfesor(@Body Profesor obj);
    @DELETE("profesor/eliminar/{id}")
    public abstract Call<Profesor> removeProfesor(@Path("id") String id);
}
