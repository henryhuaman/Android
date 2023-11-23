package com.example.homecourseandroid.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.homecourseandroid.Api.UsuarioServiceAPI;
import com.example.homecourseandroid.Model.Usuario;
import com.example.homecourseandroid.R;
import com.example.homecourseandroid.Util.ConexionREST;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroFragment extends Fragment {

    private EditText editTextNombre, editTextCorreo, editTextContrasenas, editTextDNI;
    private UsuarioServiceAPI usuarioServiceAPI;
    private SessionManager sessionManager;

    public RegistroFragment() {

    }

    public static RegistroFragment newInstance() {
        return new RegistroFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registro, container, false);


        editTextNombre = view.findViewById(R.id.editTextNombre);
        editTextCorreo = view.findViewById(R.id.editTextCorreo);
        editTextContrasenas = view.findViewById(R.id.editTextContrasena);
        editTextDNI = view.findViewById(R.id.editTextDNI);
        Button btnRegistro = view.findViewById(R.id.btnRegistro);

        sessionManager = SessionManager.getInstance(getContext());
        usuarioServiceAPI = ConexionREST.getConnection().create(UsuarioServiceAPI.class);

        // al darle boton que haga lo de registrar usuario
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });

        return view;
    }

    private void registrarUsuario() {
        // Obtiene los datos de los EditText
        String nombre = editTextNombre.getText().toString();
        String correo = editTextCorreo.getText().toString();
        String contraseña = editTextContrasenas.getText().toString();
        String dni = editTextDNI.getText().toString();

        // para que le salgan que tiene que completar

        if (nombre.isEmpty() || correo.isEmpty() || contraseña.isEmpty() || dni.isEmpty()) {

            Toast.makeText(getContext(), "Completa todos los campos", Toast.LENGTH_LONG).show();
            return;
        }


        // Crea el usuario
        Usuario nuevoUsuario = new Usuario("US004", nombre, correo, contraseña, dni);

        // Llama al api
        registrarUsuarioEnAPI(nuevoUsuario);
    }

    private void registrarUsuarioEnAPI(Usuario usuario) {
        Call<Usuario> call = usuarioServiceAPI.registrarUsuario(usuario);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Usuario registrado = response.body();
                    sessionManager.loginUser(registrado.getId());
                    Toast.makeText(getContext(), "Registro exitoso", Toast.LENGTH_LONG).show();
                    // Redirige a la actividad principal o realiza otras acciones necesarias
                    startActivity(new Intent(getContext(), MainActivity.class));
                    getActivity().finish();
                } else {
                    String errorMessage = "Error en el registro. Código: " + response.code();
                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(getContext(), "Error en la solicitud: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }
}