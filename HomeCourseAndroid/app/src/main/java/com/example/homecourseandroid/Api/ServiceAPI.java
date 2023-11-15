package com.example.homecourseandroid.Api;
import com.example.homecourseandroid.Model.Curso;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ServiceAPI
{
    @GET("producto")
    public abstract Call<List<Curso>> listCursos();
    @POST("producto/agregar")
    public abstract Call<Curso> addCurso(@Body Curso obj);
    @PUT("/producto/modificar")
    public abstract  Call<Curso> modifyCurso(@Body Curso obj);
    @DELETE("producto/eliminar/{id}")
    public abstract Call<Curso> removeCurso(@Path("id") int id);
}
