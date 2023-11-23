package com.example.homecourseandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.homecourseandroid.R;

public class UsuarioActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        toolbar = findViewById(R.id.toolbarUsu);
        sessionManager = SessionManager.getInstance(this);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(sessionManager.isLoggedIn()){
            String id = sessionManager.getUserId().substring(0,2);
            Toast.makeText(this,id,Toast.LENGTH_LONG).show();
            if(id.equals("US")) {
                getMenuInflater().inflate(R.menu.navbar_usuario, menu);
            } else if (id.equals("PR")) {
                getMenuInflater().inflate(R.menu.navbar_principal, menu);
            }
        }else{
            getMenuInflater().inflate(R.menu.navbar_principal, menu);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.itCursos) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.FragUsu, new CursoFragment()).addToBackStack(null).commit();
            return true;
        } else if (id == R.id.itTusCursos) {

        } else if (id == R.id.itProfesores) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.FragUsu, new ProfesorFragment()).addToBackStack(null).commit();
        } else if (id == R.id.itCarrito) {
            // Acción cuando se selecciona la opción del carrito
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.FragUsu, new CarritoFragment()).addToBackStack(null)
                    .commit();
            return true;
        } else if (id == R.id.itVerPerfil) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.FragUsu, new PerfilFragment()).addToBackStack(null)
                    .commit();
            return true;


        } else if (id == R.id.itVolver) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStack(); // Retrocede al fragmento anterior
            } else {
                // Si no hay fragmentos en el historial de retroceso, cierra la actividad
                finish();
            }
        } else if (id == R.id.itCerrarSesion) {
            sessionManager.logoutUser();
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}