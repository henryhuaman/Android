package com.example.homecourseandroid.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IniciarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IniciarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private EditText eTIniMail, eTIniPassword;
    private Button btnIniLogin;
    private UsuarioServiceAPI usuarioServiceAPI;
    private SessionManager sessionManager;
    public IniciarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IniciarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IniciarFragment newInstance(String param1, String param2) {
        IniciarFragment fragment = new IniciarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           // mParam1 = getArguments().getString(ARG_PARAM1);
           // mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_iniciar, container, false);
        eTIniMail = v.findViewById(R.id.eTIniMail);
        eTIniPassword = v.findViewById(R.id.eTIniPassword);
        btnIniLogin = v.findViewById(R.id.btnIniLogin);

        sessionManager = SessionManager.getInstance(getContext());
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
                                    Toast.makeText(getContext(),"Exitoso "+lstUsu.stream().filter(u->u.getId().equals(sessionManager.getUserId())).findFirst().get().getNombre(),Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getContext(), UsuarioActivity.class));
                                    getActivity().finish();
                                }else{
                                    getActivity().recreate();
                                }
                            }
                            else {
                                getActivity().recreate();
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
                        Toast.makeText(getContext(),"Ocurrio un error",Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
        return v;
    }
}