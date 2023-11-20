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
import android.widget.TextView;
import android.widget.Toast;

import com.example.homecourseandroid.Adapter.CarritoAdapter;
import com.example.homecourseandroid.Model.CarritoDeCompras;
import com.example.homecourseandroid.R;
import com.example.homecourseandroid.Util.CarritoRepository;

import java.util.ArrayList;
import java.util.List;

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
    private TextView txt;
    private CarritoAdapter carritoAdapter;
    private RecyclerView recyclerViewCar;

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

        mostrarCarrito();


        return v;
    }

    void mostrarCarrito(){
        List<CarritoDeCompras> c = carrito.getAllCarritoDeCompras();
        if(c.isEmpty()){
            Toast.makeText(getContext(), "No tienes nada en tu carrito", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getContext(),c.stream().findFirst().get().getNomCur(),Toast.LENGTH_LONG).show();
            carritoAdapter = new CarritoAdapter(c,getContext());
            recyclerViewCar.setHasFixedSize(true);
            recyclerViewCar.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerViewCar.setAdapter(carritoAdapter);
        }

    }
}