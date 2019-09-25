package com.example.madprojectx.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madprojectx.R;
import com.example.madprojectx.interfaces.ItemClickListener;

public class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public TextView reviewText, reviewName;
    public ItemClickListener listener;

    public ReviewViewHolder(@NonNull View itemView) {
        super(itemView);

        reviewText = itemView.findViewById(R.id.reviewProp);
        reviewName = itemView.findViewById(R.id.reviewName);

    }

    public void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition(), false);
    }
}
