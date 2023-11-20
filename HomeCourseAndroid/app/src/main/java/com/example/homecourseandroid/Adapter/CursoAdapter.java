package com.example.homecourseandroid.Adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.homecourseandroid.Activities.DetallesCursoFragment;
import com.example.homecourseandroid.Activities.MainActivity;
import com.example.homecourseandroid.Model.Curso;
import com.example.homecourseandroid.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CursoAdapter extends RecyclerView.Adapter<CursoAdapter.ViewHolder>{
    private List<Curso> cursos;
    private  List<Curso> cursosSearch;
    private List<Curso> cursosSpn;
    private LayoutInflater mInflater;
    private Context context;

    public CursoAdapter(List<Curso> cursos, Context context) {
        this.cursos = cursos;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        cursosSearch = new ArrayList<>();
        cursosSearch.addAll(cursos);
    }

    @Override
    public int getItemCount() {
        return cursos.size();
    }

    @NonNull
    @Override
    public CursoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_cursos, null);
        return new CursoAdapter.ViewHolder(view);
    }

    public void filtradoSpn(String opc){
        cursos.clear();
        if(opc.equals("Sin filtro")){
            // Si la cadena de búsqueda está vacía, restaura la lista original
            cursos.addAll(cursosSearch);
        } else {
            // Filtrar en una nueva lista
            List<Curso> coleccion = cursosSearch.stream().filter(i ->i.getCategoria().toLowerCase().contains(opc.toLowerCase()))
                    .collect(Collectors.toList());
            cursos.clear();
            cursos.addAll(coleccion);
        }
        notifyDataSetChanged();
    }

    public void filtradoSearch(String busc){
        cursos.clear();
        if(busc.length() == 0){
            // Si la cadena de búsqueda está vacía, restaura la lista original
            cursos.addAll(cursosSearch);
        } else {
            // Filtrar en una nueva lista
            List<Curso> coleccion = cursosSearch.stream().filter(i ->
                            i.getNombre().toLowerCase().contains(busc.toLowerCase()) ||
                                    i.getCategoria().toLowerCase().contains(busc.toLowerCase()) ||
                                    String.valueOf(i.getPrecio()).toLowerCase().contains(busc.toLowerCase()))
                    .collect(Collectors.toList());
            cursos.clear();
            cursos.addAll(coleccion);
        }
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull final CursoAdapter.ViewHolder holder, int position) {
        holder.bindData(cursos.get(position));
    }

    public void setItems(List<Curso> items) {
        cursos = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView nombre, categoria, precio;
        LinearLayout verDet;

        ViewHolder(View itemView){
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImage);
            nombre = itemView.findViewById(R.id.nameTextView);
            categoria = itemView.findViewById(R.id.cityTextView);
            precio = itemView.findViewById(R.id.statusTextView);
            verDet = itemView.findViewById(R.id.carddd);
        }

        void bindData(final Curso item){
            //iconImage.setColorFilter(Color.parseColor());

            nombre.setText("");
            categoria.setText("Categoria: ");
            precio.setText("Precio: ");

            nombre.setText(item.getNombre());
            categoria.append(item.getCategoria());
            precio.append(String.valueOf(item.getPrecio()));
            verDet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Intent intent = new Intent(context, DetallesCursoActivity.class);
                    //intent.putExtra("Curso", item);
                    //context.startActivity(intent);
                    DetallesCursoFragment fragment = new DetallesCursoFragment();
                    Bundle args = new Bundle();
                    args.putSerializable("curso",item);
                    fragment.setArguments(args);
                    FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.FragPrin,fragment).commit();
                }
            });

        }
    }


}
