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

public interface CursoServiceAPI
{
    @GET("curso")
    public abstract Call<List<Curso>> listCursos();
    @POST("curso/agregar")
    public abstract Call<Curso> addCurso(@Body Curso obj);
    @PUT("/curso/modificar")
    public abstract  Call<Curso> modifyCurso(@Body Curso obj);
    @DELETE("curso/eliminar/{id}")
    public abstract Call<Curso> removeCurso(@Path("id") String id);
}
