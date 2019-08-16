package com.example.foodorder.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorder.R;
import com.example.foodorder.interfaces.ItemClickListener;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView orderId,orderStatus,orderPhone,orderAddress,orderDate,orderTime;



    private ItemClickListener itemClickListener;
    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        orderId = itemView.findViewById(R.id.order_id);
        orderStatus = itemView.findViewById(R.id.order_status);
        orderPhone = itemView.findViewById(R.id.order_phone);
        orderAddress = itemView.findViewById(R.id.order_address);
        orderDate = itemView.findViewById(R.id.order_date);
        orderTime = itemView.findViewById(R.id.order_time);

        //itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
       itemClickListener.onClick(view,getAdapterPosition(),false);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
