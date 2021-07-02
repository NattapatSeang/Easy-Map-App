package com.example.easymap;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterMap extends RecyclerView.Adapter<AdapterMap.ViewHolder>{

    private static final String TAG = "AdapterMap";
    private ArrayList<Map> maps = new ArrayList<Map>();
    private Context context;

    public AdapterMap(ArrayList<Map> maps, Context context) {
        this.maps = maps;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_layout_map, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterMap.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        String detail = maps.get(position).getDescription();
        String name = maps.get(position).getName();

        holder.detail.setText(detail);
        holder.mapName.setText(name);

        holder.relLayoutMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = maps.get(position).get_id();
                String name_map = maps.get(position).getName();
                Log.d(TAG, "onClickLister: OK: " + id);

                //Toast.makeText(context, id, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), MapShow.class);
                intent.putExtra("MAP_ID", id);
                intent.putExtra("MAP_NAME", name_map);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return maps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView mapName;
        TextView detail;
        RelativeLayout relLayoutMap;

        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            mapName = itemView.findViewById(R.id.mapName);
            detail = itemView.findViewById(R.id.mapDetail);
            relLayoutMap = itemView.findViewById(R.id.relLayoutMap);
        }
    }
}
