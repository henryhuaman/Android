package com.example.homecourseandroid.Activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviderGetKt;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homecourseandroid.Adapter.CarritoAdapter;
import com.example.homecourseandroid.Api.InscripcionServiceAPI;
import com.example.homecourseandroid.Model.CarritoDeCompras;
import com.example.homecourseandroid.Model.Inscripcion;
import com.example.homecourseandroid.Model.InscripcionRepository;
import com.example.homecourseandroid.R;
import com.example.homecourseandroid.Util.CarritoRepository;
import com.example.homecourseandroid.Util.ConexionREST;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarritoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarritoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private CarritoRepository carrito;
    private Button btnCarComprar;
    private CarritoAdapter carritoAdapter;
    private RecyclerView recyclerViewCar;
    //private InscripcionServiceAPI inscripcionServiceAPI;
    private InscripcionRepository inscripcionRepository;

    public CarritoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CarritoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CarritoFragment newInstance(String param1, String param2) {
        CarritoFragment fragment = new CarritoFragment();
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
        View v = inflater.inflate(R.layout.fragment_carrito, container, false);
        recyclerViewCar = v.findViewById(R.id.recyclerViewCarrito);
        carrito = CarritoRepository.getInstance();
        btnCarComprar =v.findViewById(R.id.btnCarComprar);
        inscripcionRepository = new InscripcionRepository(getContext());


        mostrarCarrito();
        //******************
        EditText ed = v.findViewById(R.id.editTextTextMultiLine);

        for (Inscripcion a:inscripcionRepository.getInscripciones()) {
            ed.append(a.getCodOpe());
        }
        //******************


        btnCarComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SessionManager.getInstance(getContext()).isLoggedIn()){
                    Toast.makeText(getContext(), ""+CarritoRepository.getInstance().getAllCarritoDeCompras().stream().count(), Toast.LENGTH_SHORT).show();
                    agregar(SessionManager.getInstance(getContext()).getUserId());
                    getActivity().recreate();

                }else{
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.FragPrin,new IniciarFragment())
                            .addToBackStack(null).commit();
                }
            }
        });

        return v;
    }

    void agregar(String idUsu){
        List<Inscripcion> lst = new ArrayList<>();
        for (CarritoDeCompras c:carrito.getAllCarritoDeCompras()) {
            Inscripcion ins = new Inscripcion();
            ins.setUsuarioId(idUsu);
            ins.setCursoId(c.getIdCur());
            ins.setInsFecha(LocalDate.now().atStartOfDay().toString());

            if(!inscripcionRepository.getInscripciones().stream()
                    .anyMatch(i->i.getCursoId().equals(ins.getCursoId())
                    && i.getUsuarioId().equals(ins.getUsuarioId()))){
                lst.add(ins);
            }else{
                Toast.makeText(getContext(), "no se pudo", Toast.LENGTH_SHORT).show();
            }
        }

        String iD = inscripcionRepository.generarCodigo();
        for (Inscripcion lt: lst) {
            Inscripcion aux = lt;
            aux.setCodOpe(iD);
            if (!aux.getCursoId().isEmpty() && !aux.getUsuarioId().isEmpty()) {
                // Realiza la solicitud al servidor
                inscripcionRepository.agregar(aux);
            } else {
                // Maneja el caso en el que cursoId o usuarioId sea nulo o una cadena vacía
                Toast.makeText(getContext(), "cursoId y usuarioId no pueden ser nulos o vacíos", Toast.LENGTH_SHORT).show();
            }

        }
        carrito.deleteAll();
    }

    void mostrarCarrito(){

        List<CarritoDeCompras> c = carrito.getAllCarritoDeCompras();
        if(c.isEmpty()){
            Toast.makeText(getContext(), "No tienes nada en tu carrito", Toast.LENGTH_SHORT).show();
        }else {
                carritoAdapter = new CarritoAdapter(c,getContext());
                recyclerViewCar.setHasFixedSize(true);
                recyclerViewCar.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerViewCar.setAdapter(carritoAdapter);

        }


    }




}