package com.example.homecourseandroid.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.homecourseandroid.Model.CarritoDeCompras;
import com.example.homecourseandroid.Model.Curso;
import com.example.homecourseandroid.R;
import com.example.homecourseandroid.Util.CarritoRepository;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetallesCursoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetallesCursoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private TextView nomCur;
    private TextView desCur;
    private TextView proCur;
    private Button btnVolver, btnAddCarr;
    private Curso curso;
    public DetallesCursoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetallesCursoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetallesCursoFragment newInstance(String param1, String param2) {
        DetallesCursoFragment fragment = new DetallesCursoFragment();
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
            curso = (Curso) getArguments().getSerializable("curso");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detalles_curso, container, false);



        nomCur = v.findViewById(R.id.vtNombre);
        desCur = v.findViewById(R.id.vtDescripcion);
        proCur = v.findViewById(R.id.vtProfesor);
        btnVolver = v.findViewById(R.id.btnVolver);
        btnAddCarr = v.findViewById(R.id.btnAddCarr);

        nomCur.append(curso.getNombre());
        desCur.append(curso.getDescripcion());
        proCur.append(curso.getProfesorId());

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
            }
        });

        btnAddCarr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CarritoDeCompras c = new CarritoDeCompras(curso.getId(),curso.getNombre(),curso.getCategoria(),curso.getPrecio());
                CarritoRepository.getInstance().add(c);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FragPrin,new CarritoFragment()).commit();
            }
        });



        return v;
    }
}