package com.example.homecourseandroid.Activities;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.homecourseandroid.Adapter.ProfesorAdapter;
import com.example.homecourseandroid.Api.ProfesorServiceAPI;
import com.example.homecourseandroid.Model.Profesor;
import com.example.homecourseandroid.R;
import com.example.homecourseandroid.Util.ConexionREST;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfesorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfesorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public ProfesorServiceAPI profesorServiceAPI;
    private RecyclerView recyclerViewProf;
    private ProfesorAdapter profesorAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfesorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfesorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfesorFragment newInstance(String param1, String param2) {
        ProfesorFragment fragment = new ProfesorFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profesor, container, false);
        recyclerViewProf = v.findViewById(R.id.ListRecyclerProf);

        profesorServiceAPI = ConexionREST.getConnection().create(ProfesorServiceAPI.class);
        cargarDatosDelAPI();
        return v;
    }

    void cargarDatosDelAPI(){
        Call<List<Profesor>> call = profesorServiceAPI.listProfesores();
        call.enqueue(new Callback<List<Profesor>>() {
            @Override
            public void onResponse(Call<List<Profesor>> call, Response<List<Profesor>> response) {
                if(response.isSuccessful())
                {
                    List<Profesor> lstProf = response.body();

                    profesorAdapter = new ProfesorAdapter(lstProf, getContext());
                    recyclerViewProf.setHasFixedSize(true);
                    recyclerViewProf.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerViewProf.setAdapter(profesorAdapter);
                }else
                {
                    Toast.makeText(null,"Error",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Profesor>> call, Throwable t) {
                System.err.println("Error en la solicitud: " + t.getMessage());
                System.err.println("StackTrace: ");
                t.printStackTrace(); // Esto imprimirá la traza de la pila para obtener más detalles del error.
                Toast.makeText(getContext(),"Ocurrio un error",Toast.LENGTH_LONG).show();

            }
        });
    }
}