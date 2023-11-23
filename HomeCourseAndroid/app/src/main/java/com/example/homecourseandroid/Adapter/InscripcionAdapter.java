package com.example.homecourseandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homecourseandroid.Model.Inscripcion;
import com.example.homecourseandroid.R;

import java.util.List;

public class InscripcionAdapter extends RecyclerView.Adapter<InscripcionAdapter.ViewHolder>{
    private Context context;
    private List<Inscripcion> inscripcions;
    private LayoutInflater mInflater;
    public InscripcionAdapter(Context context, List<Inscripcion> inscripcions) {
        this.context = context;
        this.inscripcions = inscripcions;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_inscripciones , null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(inscripcions.get(position));
    }

    @Override
    public int getItemCount() {
        return inscripcions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewCodigo, textViewCurso, textViewProfesor, textViewCategoria;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCodigo = itemView.findViewById(R.id.textViewCodigo);
            textViewCurso = itemView.findViewById(R.id.textViewCurso);
            textViewProfesor = itemView.findViewById(R.id.textViewProfesor);
            textViewCategoria = itemView.findViewById(R.id.textViewCategoria);
        }

        void bindData(final Inscripcion item){
            textViewCodigo.setText("Código de Inscripción: "+ item.getCodOpe());
            textViewCurso.setText("Fecha: "+ item.getInsFecha());
        }
    }
}
