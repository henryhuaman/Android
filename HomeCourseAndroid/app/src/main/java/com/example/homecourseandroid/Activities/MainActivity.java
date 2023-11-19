package com.example.homecourseandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.homecourseandroid.R;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionManager = SessionManager.getInstance(this);
        toolbar = findViewById(R.id.toolbarr);
        setSupportActionBar(toolbar);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.FragPrin, new PrincipalFragment());
        ft.commit();

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

        if (id == R.id.itIniciarSesion) {
            // Acción cuando se selecciona la opción de cursos
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.FragPrin, new IniciarFragment())
                    .commit();
            return true;
        } else if (id == R.id.itRegistrarse) {
            // Acción cuando se selecciona la opción de profesores
            return true;
        } else if (id == R.id.itCarrito) {
            // Acción cuando se selecciona la opción del carrito

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