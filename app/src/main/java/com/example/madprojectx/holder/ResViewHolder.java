package com.example.madprojectx.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madprojectx.R;
import com.example.madprojectx.interfaces.ItemClickListener;

public class ResViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView res_hostel, res_book_id, res_name, res_phone;
    public ItemClickListener listener;

    public ResViewHolder(@NonNull View itemView) {
        super(itemView);

        res_hostel = itemView.findViewById(R.id.res_prop_name);
        res_book_id = itemView.findViewById(R.id.res_prop_booking);
        res_name = itemView.findViewById(R.id.res_prop_booker);
        res_phone = itemView.findViewById(R.id.res_prop_phone);
    }

    public void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view)  {
        listener.onClick(view, getAdapterPosition(), false);
    }

}
