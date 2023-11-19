package com.example.homecourseandroid.Api;

import com.example.homecourseandroid.Model.Profesor;
import com.example.homecourseandroid.Model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsuarioServiceAPI {

    @GET("usuario")
    public abstract Call<List<Usuario>> listUsuarios();
    @POST("usuario/agregar")
    public abstract Call<Usuario> addUsuarios(@Body Usuario obj);
    @PUT("/usuario/modificar")
    public abstract  Call<Usuario> modifyUsuarios(@Body Usuario obj);
    @DELETE("usuario/eliminar/{id}")
    public abstract Call<Usuario> removeUsuarios(@Path("id") String id);
}
