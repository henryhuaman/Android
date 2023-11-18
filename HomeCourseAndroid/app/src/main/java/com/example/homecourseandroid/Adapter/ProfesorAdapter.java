package com.example.homecourseandroid.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homecourseandroid.Activities.DetallesProfesorActivity;
import com.example.homecourseandroid.Model.Profesor;
import com.example.homecourseandroid.R;

import java.util.List;

public class ProfesorAdapter extends RecyclerView.Adapter<ProfesorAdapter.ViewHolder> {
    private List<Profesor> profesores;
    private Context context;
    private LayoutInflater mInflater;

    public ProfesorAdapter(List<Profesor> profesores, Context context) {
        this.profesores = profesores;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_profesores,null);
        return new ProfesorAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(profesores.get(position));
    }

    @Override
    public int getItemCount() {
        return profesores.size();
    }
    public void setItems(List<Profesor> items) {
        profesores = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nombreProf, descr, correo;
        ConstraintLayout verDet;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreProf = itemView.findViewById(R.id.nameProfTextView);
            correo = itemView.findViewById(R.id.correoProfTextView);
            descr = itemView.findViewById(R.id.descProfTextView);
            verDet = itemView.findViewById(R.id.cardProf);
        }

        void bindData(final Profesor item){
            nombreProf.setText("Nombre: ");
            correo.setText("Correo :");
            descr.setText("Descripcion: ");


            nombreProf.append(item.getNombre());
            correo.append(item.getCorreo());
            descr.append(item.getDescripcion());
            verDet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, DetallesProfesorActivity.class);
                    i.putExtra("Profesor", item);
                    context.startActivity(i);
                }
            });

        }
    }
}
