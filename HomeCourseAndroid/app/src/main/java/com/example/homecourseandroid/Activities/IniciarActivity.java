package com.example.homecourseandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.homecourseandroid.Api.UsuarioServiceAPI;
import com.example.homecourseandroid.Model.Usuario;
import com.example.homecourseandroid.R;
import com.example.homecourseandroid.Util.ConexionREST;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IniciarActivity extends AppCompatActivity {
    private EditText eTIniMail, eTIniPassword;
    private Button btnIniLogin;
    private Toolbar toolbar;
    private UsuarioServiceAPI usuarioServiceAPI;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar);
        eTIniMail = findViewById(R.id.eTIniMail);
        eTIniPassword = findViewById(R.id.eTIniPassword);
        btnIniLogin = findViewById(R.id.btnIniLogin);
        toolbar = findViewById(R.id.toolbarIniciar);
        setSupportActionBar(toolbar);

        sessionManager = SessionManager.getInstance(this);

        usuarioServiceAPI = ConexionREST.getConnection().create(UsuarioServiceAPI.class);

        btnIniLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<List<Usuario>> call = usuarioServiceAPI.listUsuarios();
                call.enqueue(new Callback<List<Usuario>>() {
                    @Override
                    public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                        if(response.isSuccessful())
                        {
                            List<Usuario> lstUsu = response.body();
                            String mail = eTIniMail.getText().toString();
                            String pass = eTIniPassword.getText().toString();

                            if(lstUsu.stream().anyMatch(u -> u.getCorreo().equals(mail))){
                                if(lstUsu.stream().anyMatch(u -> u.getCorreo().equals(mail))){
                                    Usuario user = lstUsu.stream().filter(u->u.getCorreo().equals(mail) || u.getContraseña().equals(pass)).findFirst().get();
                                    sessionManager.loginUser(user.getId());
                                    Toast.makeText(getApplicationContext(),"Exitoso "+lstUsu.stream().filter(u->u.getId().equals(sessionManager.getUserId())).findFirst().get().getNombre(),Toast.LENGTH_LONG).show();


                                }else{
                                    recreate();
                                }
                            }
                            else {
                                recreate();
                            }

                        }else
                        {
                            Toast.makeText(null,"Error",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Usuario>> call, Throwable t) {
                        System.err.println("Error en la solicitud: " + t.getMessage());
                        System.err.println("StackTrace: ");
                        t.printStackTrace(); // Esto imprimirá la traza de la pila para obtener más detalles del error.
                        Toast.makeText(IniciarActivity.this,"Ocurrio un error",Toast.LENGTH_LONG).show();

                    }
                });
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navbar_principal, menu);
        configurarVisibilidadMenu(menu);
        return true;
    }

    private void configurarVisibilidadMenu(Menu menu) {
        MenuItem itemIniciarSesion = menu.findItem(R.id.itIniciarSesion);
        MenuItem itemRegistrarse = menu.findItem(R.id.itRegistrarse);
        MenuItem itemTusCursos = menu.findItem(R.id.itTusCursos);
        MenuItem itemProfesores = menu.findItem(R.id.itProfesores);
        MenuItem itemCursos = menu.findItem(R.id.itCursos);
        MenuItem itemCerrarSesion = menu.findItem(R.id.itCerrarSesion);

        Toast.makeText(IniciarActivity.this,""+sessionManager.isLoggedIn(),Toast.LENGTH_LONG).show();
        Toast.makeText(IniciarActivity.this,""+sessionManager.getUserId(),Toast.LENGTH_LONG).show();
        if(!sessionManager.isLoggedIn()){
            itemCursos.setVisible(false);
            itemProfesores.setVisible(false);
            itemCerrarSesion.setVisible(false);
            itemTusCursos.setVisible(false);
        }else{
            itemIniciarSesion.setVisible(false);
            itemRegistrarse.setVisible(false);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.itIniciarSesion) {
            // Acción cuando se selecciona la opción de cursos
            startActivity(new Intent(getApplicationContext(), IniciarActivity.class));
            return true;
        } else if (id == R.id.itRegistrarse) {
            // Acción cuando se selecciona la opción de profesores
            return true;
        } else if (id == R.id.itCarrito) {
            // Acción cuando se selecciona la opción del carrito

            return true;
        } else if (id == R.id.itVolver) {
            finish();
        } else if (id == R.id.itCerrarSesion) {
            sessionManager.logoutUser();
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
            return super.onOptionsItemSelected(item);
        }
}