package com.example.homecourseandroid.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homecourseandroid.Model.Curso;
import com.example.homecourseandroid.R;

import java.util.List;

public class CursoAdapter extends RecyclerView.Adapter<CursoAdapter.ViewHolder>{
    private List<Curso> cursos;
    private LayoutInflater mInflater;
    private Context context;

    public CursoAdapter(List<Curso> cursos, Context context) {
        this.cursos = cursos;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
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

    @Override
    public void onBindViewHolder(@NonNull final CursoAdapter.ViewHolder holder, int position) {
        holder.bindData(cursos.get(position));
    }

    public void setItems(List<Curso> items) {
        cursos = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView name, city,status;

        ViewHolder(View itemView){
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImage);
            name = itemView.findViewById(R.id.nameTextView);
            city = itemView.findViewById(R.id.cityTextView);
            status = itemView.findViewById(R.id.statusTextView);

        }

        void bindData(final Curso item){
            //iconImage.setColorFilter(Color.parseColor());
            name.setText(item.getNombre());
            city.setText(item.getCategoria());
            status.setText(String.valueOf(item.getPrecio()));
        }
    }


}
