package com.example.homecourseandroid.Activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homecourseandroid.Api.UsuarioServiceAPI;
import com.example.homecourseandroid.Model.Usuario;
import com.example.homecourseandroid.R;
import com.example.homecourseandroid.Util.ConexionREST;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditUsuarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditUsuarioFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    // TODO: Rename and change types of parameters
    private EditText etEdUsuNombre, etEdUsuCorreo, etEdUsuDni, etEdUsuContra;
    private Button btnEdUsuConfirmar;
    private Usuario usuario;
    private UsuarioServiceAPI usuarioServiceAPI;

    public EditUsuarioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditUsuarioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditUsuarioFragment newInstance(String param1, String param2) {
        EditUsuarioFragment fragment = new EditUsuarioFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            usuario = (Usuario) getArguments().getSerializable("usuarioEdit");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_usuario, container, false);
        etEdUsuNombre = v.findViewById(R.id.etEdUsuNombre);
        etEdUsuCorreo = v.findViewById(R.id.etEdUsuCorreo);
        etEdUsuDni = v.findViewById(R.id.etEdUsuDni);
        etEdUsuContra = v.findViewById(R.id.etEdUsuContra);
        btnEdUsuConfirmar = v.findViewById(R.id.btnEdUsuConfirmar);
        usuarioServiceAPI = ConexionREST.getConnection().create(UsuarioServiceAPI.class);

        etEdUsuNombre.setText(usuario.getNombre());
        etEdUsuCorreo.setText(usuario.getCorreo());
        etEdUsuDni.setText(usuario.getDni());
        etEdUsuContra.setText(usuario.getContraseña());

        btnEdUsuConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etEdUsuNombre.getText().toString().isEmpty() && !etEdUsuCorreo.getText().toString().isEmpty()
                && !etEdUsuDni.getText().toString().isEmpty() && !etEdUsuContra.getText().toString().isEmpty()){
                    usuario.setNombre(etEdUsuNombre.getText().toString());
                    usuario.setCorreo(etEdUsuCorreo.getText().toString());
                    usuario.setContraseña(etEdUsuContra.getText().toString());
                    usuario.setDni(etEdUsuDni.getText().toString());
                    Call<Usuario> call = usuarioServiceAPI.modifyUsuarios(usuario);
                    call.enqueue(new Callback<Usuario>() {
                        @Override
                        public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(getActivity(), "usuario "+ response.body().getId()+ " editado exitosamente", Toast.LENGTH_SHORT).show();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentManager.popBackStack(); // Retrocede al fragmento anterior

                            }else{
                                Toast.makeText(getActivity(), "error" + response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Usuario> call, Throwable t) {
                            Toast.makeText(getActivity(), "Error: "+ t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }



            }
        });

        return v;
    }

    void modificarUsuario(){

    }
}