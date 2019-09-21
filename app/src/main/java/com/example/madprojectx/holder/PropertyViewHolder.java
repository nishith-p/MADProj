package com.example.madprojectx.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madprojectx.R;
import com.example.madprojectx.interfaces.ItemClickListener;

public class PropertyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public TextView propName, propLoc, propType, propRent;
    public ItemClickListener listener;

    public PropertyViewHolder(@NonNull View itemView)
    {
        super(itemView);

        propName = itemView.findViewById(R.id.pp_main_name);
        propLoc = itemView.findViewById(R.id.pp_main_loc);
        propType = itemView.findViewById(R.id.pp_main_type);
        propRent = itemView.findViewById(R.id.pp_main_price);

    }

    public void setItemClickListener(ItemClickListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onClick(View view)
    {
        listener.onClick(view, getAdapterPosition(), false);

    }
}
