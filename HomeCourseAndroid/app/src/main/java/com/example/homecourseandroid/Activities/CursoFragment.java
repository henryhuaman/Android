package com.example.homecourseandroid.Activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.homecourseandroid.Adapter.CursoAdapter;
import com.example.homecourseandroid.Api.CursoServiceAPI;
import com.example.homecourseandroid.Model.Curso;
import com.example.homecourseandroid.R;
import com.example.homecourseandroid.Util.ConexionREST;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CursoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CursoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    public CursoServiceAPI cursoServiceAPI;
    private RecyclerView recyclerView;
    private CursoAdapter cursoAdapter;
    private SearchView txtBuscar;
    private Spinner spnCate;
    private Button btnFiltrar;

    public CursoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CursoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CursoFragment newInstance(String param1, String param2) {
        CursoFragment fragment = new CursoFragment();
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
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_curso, container, false);
        recyclerView = v.findViewById(R.id.ListRecycler);
        txtBuscar = v.findViewById(R.id.txtBuscar);
        spnCate = v.findViewById(R.id.spnCategoria);
        btnFiltrar = v.findViewById(R.id.btnFiltrar);
        cursoServiceAPI = ConexionREST.getConnection().create(CursoServiceAPI.class);
        cargarDatosDelAPI();

        txtBuscar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                cursoAdapter.filtradoSearch(newText);
                return false;
            }
        });

        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selected = spnCate.getSelectedItem().toString();
                cursoAdapter.filtradoSpn(selected);
            }
        });

        return v;
    }

    void cargarDatosDelAPI(){
        Call<List<Curso>> call = cursoServiceAPI.listCursos();
        call.enqueue(new Callback<List<Curso>>() {
            @Override
            public void onResponse(Call<List<Curso>> call, Response<List<Curso>> response) {
                if(response.isSuccessful())
                {
                    List<Curso> lstCurso = response.body();

                    cursoAdapter = new CursoAdapter(lstCurso, getContext());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(cursoAdapter);
                    //crea una lista con las categorias existentes
                    List<String> categoria = new ArrayList<>();
                    categoria.add("Sin filtro");
                    categoria.addAll(lstCurso.stream().map(s->s.getCategoria()).distinct().collect(Collectors.toList()));
                    //se crea un adaptador para q el spinner pueda usar la lista.
                    ArrayAdapter array = new ArrayAdapter(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item ,categoria);
                    //establecer el diseno del spinner
                    array.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //se establece el adaptador al spinner
                    spnCate.setAdapter(array);
                }else
                {
                    Toast.makeText(getContext(),"Error",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Curso>> call, Throwable t) {
                System.err.println("Error en la solicitud: " + t.getMessage());
                System.err.println("StackTrace: ");
                t.printStackTrace(); // Esto imprimirá la traza de la pila para obtener más detalles del error.
                Toast.makeText(getContext(),"Ocurrio un error",Toast.LENGTH_LONG).show();

            }
        });
    }
}