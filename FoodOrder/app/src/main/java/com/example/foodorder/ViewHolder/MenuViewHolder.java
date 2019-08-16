package com.example.foodorder.ViewHolder;

import android.content.ClipData;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorder.R;
import com.example.foodorder.interfaces.ItemClickListener;


public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
  public   TextView txtMenuName;
     public ImageView imgView;
   public  ItemClickListener itemClickListener;

    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);
        txtMenuName = itemView.findViewById(R.id.menu_name);
        imgView = itemView.findViewById(R.id.menu_image);
        itemView.setOnClickListener(this);



    }


    public  void setItemClickListener(ItemClickListener itmClickListener){
        this.itemClickListener = itmClickListener;


    }

    @Override
    public void onClick(View v ) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
