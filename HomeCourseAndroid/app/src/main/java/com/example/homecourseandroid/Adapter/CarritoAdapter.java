package com.example.homecourseandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homecourseandroid.Model.CarritoDeCompras;
import com.example.homecourseandroid.R;

import java.util.List;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.ViewHolder>{
    private List<CarritoDeCompras> carritos;
    private Context context;
    private LayoutInflater mInflater;

    public CarritoAdapter(List<CarritoDeCompras> carritos, Context context) {
        this.carritos = carritos;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CarritoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_carrito, null);
        return new CarritoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarritoAdapter.ViewHolder holder, int position) {
        holder.bindData(carritos.get(position));
    }

    @Override
    public int getItemCount() {
        return carritos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView vtCarNomCurso;
        TextView vtCarCategoria;
        Button btnCarEliminar;
        public ViewHolder(View itemView) {
            super(itemView);
            vtCarNomCurso = itemView.findViewById(R.id.vtCarNomCurso);
            vtCarCategoria = itemView.findViewById(R.id.vtCarCategoria);
            btnCarEliminar = itemView.findViewById(R.id.btnCursosF);
        }

        void bindData(final CarritoDeCompras item){

        }
    }
}
