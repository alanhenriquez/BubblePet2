package com.xforce.bubblepet2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.xforce.bubblepet2.R;

import java.util.ArrayList;
import java.util.List;

public class TargetaPetAdapter extends RecyclerView.Adapter<TargetaPetHolder>{

    private final List<TargetaPet> targetaPetList = new ArrayList<>();
    private final Context context;

    public TargetaPetAdapter(Context context) {
        this.context = context;
    }

    public void addTargetaPet(TargetaPet targetaPet){
        targetaPetList.add(targetaPet);
        notifyItemInserted(targetaPetList.size());
    }

    @NonNull
    @Override
    public TargetaPetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.targeta_pet,parent,false);
        return new TargetaPetHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TargetaPetHolder holder, int position) {
        holder.getNombre().setText(targetaPetList.get(position).getNombre());
        holder.getEdad().setText(targetaPetList.get(position).getEdad());
        holder.getColor().setText(targetaPetList.get(position).getColor());
        holder.getRaza().setText(targetaPetList.get(position).getRaza());
        holder.getSalud().setText(targetaPetList.get(position).getSalud());
        Glide.with(context).load(targetaPetList.get(position).getImagen()).into(holder.getImagen());
    }

    @Override
    public int getItemCount() {
        return targetaPetList.size();
    }
}
