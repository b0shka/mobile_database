package com.example.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    Context context;
    List<Menu> menus;

    public MenuAdapter(Context context, List<Menu> menus) {
        this.context = context;
        this.menus = menus;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View menuItems = LayoutInflater.from(context).inflate(R.layout.menu_item, parent, false);
        return new MenuViewHolder(menuItems);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        holder.menu_title.setText(menus.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return menus.size();
    }

    public static final class MenuViewHolder extends RecyclerView.ViewHolder {

        TextView menu_title = itemView.findViewById(R.id.menu_title);

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
