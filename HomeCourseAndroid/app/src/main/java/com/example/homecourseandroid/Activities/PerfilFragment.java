package com.example.homecourseandroid.Activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.homecourseandroid.Api.UsuarioServiceAPI;
import com.example.homecourseandroid.Model.Usuario;
import com.example.homecourseandroid.R;
import com.example.homecourseandroid.Util.ConexionREST;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilFragment extends Fragment {

    private TextView txtNombre, txtCorreo, txtDNI;
    private Button btnEditProfile;
    private UsuarioServiceAPI usuarioServiceAPI;
    private SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);


        sessionManager = SessionManager.getInstance(requireContext());


        txtNombre = view.findViewById(R.id.textViewNombre);
        txtCorreo = view.findViewById(R.id.textViewCorreo);
        txtDNI = view.findViewById(R.id.textViewDNI);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);


        usuarioServiceAPI = ConexionREST.getConnection().create(UsuarioServiceAPI.class);


        if (sessionManager.isLoggedIn()) {
            cargarDatosPerfil();
        } else {
            Toast.makeText(requireContext(), "Error: Sesión no iniciada", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void cargarDatosPerfil() {
        // Obtiene el ID del usuario actual desde el administrador de sesiones
        String userId = sessionManager.getUserId();


        if (userId == null || userId.isEmpty()) {
            Toast.makeText(requireContext(), "Error: ID de usuario no válido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Llama al método de la API para obtener los datos del perfil

        Call<Usuario> call = usuarioServiceAPI.getUsuario(userId);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {

                    Usuario usuario = response.body();
                    if (usuario != null) {
                        // Muestra los datos del perfil en las TextViews
                        txtNombre.setText("Nombre: " + usuario.getNombre());
                        txtCorreo.setText("Correo: " + usuario.getCorreo());
                        txtDNI.setText("DNI: " + usuario.getDni());

                        btnEditProfile.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                EditUsuarioFragment editUsuarioFragment = new EditUsuarioFragment();
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("usuarioEdit", usuario);
                                editUsuarioFragment.setArguments(bundle);
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.FragUsu, editUsuarioFragment).addToBackStack(null).commit();
                            }
                        });
                    } else {

                        Toast.makeText(requireContext(), "Error: Respuesta del servidor vacía", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(requireContext(), "Error al obtener datos del perfil. Código: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {

                Toast.makeText(requireContext(), "Error de conexión al obtener datos del perfil: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
