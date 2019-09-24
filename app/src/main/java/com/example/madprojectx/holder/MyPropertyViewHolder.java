package com.example.madprojectx.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madprojectx.R;
import com.example.madprojectx.interfaces.ItemClickListener;

public class MyPropertyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public TextView propName, propCity;
    public ImageView propEdit, propDelete;
    public ItemClickListener listener;

    public MyPropertyViewHolder(@NonNull View itemView) {
        super(itemView);

        propName = itemView.findViewById(R.id.myprop_name_sub);
        propCity = itemView.findViewById(R.id.myprop_city_sub);
        /*propEdit = itemView.findViewById(R.id.myprop_edit);
        propDelete = itemView.findViewById(R.id.myprop_delete);*/
    }

    public void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition(), false);
    }
}
