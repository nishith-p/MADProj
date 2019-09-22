package com.example.madprojectx.holder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madprojectx.R;
import com.example.madprojectx.interfaces.ItemClickListener;

public class PropertyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public TextView propName, propLoc, propType, propRent;
    public ItemClickListener listener;
    public ConstraintLayout mCon1, mCon2, mCon3;
    public ImageView propMale, propFemale, propImg;
    public Button phoneButton;

    public PropertyViewHolder(@NonNull View itemView)
    {
        super(itemView);

        mCon1 = itemView.findViewById(R.id.CONS_W);
        mCon2 = itemView.findViewById(R.id.CONS_A);
        mCon3 = itemView.findViewById(R.id.CONS_LL);

        propName = itemView.findViewById(R.id.pp_main_name);
        propLoc = itemView.findViewById(R.id.pp_main_loc);
        propType = itemView.findViewById(R.id.pp_main_type);
        propRent = itemView.findViewById(R.id.pp_main_price);

        propMale = itemView.findViewById(R.id.pp_male);
        propFemale = itemView.findViewById(R.id.pp_main_img_gender);
        propImg = itemView.findViewById(R.id.pp_main_img_thumb);

        phoneButton = itemView.findViewById(R.id.pp_main_bt_call);

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
