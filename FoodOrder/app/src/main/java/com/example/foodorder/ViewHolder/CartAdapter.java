package com.example.foodorder.ViewHolder;


import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.foodorder.R;
import com.example.foodorder.interfaces.ItemClickListener;
import com.example.foodorder.models.Order;

import java.net.ContentHandler;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView text_cart_name, text_cart_price;
    public ImageView image_cart_count;
    private ItemClickListener itemClickListener;

    public CartViewHolder(View itemView) {
        super(itemView);
        text_cart_name = itemView.findViewById(R.id.cart_item_name);
        text_cart_price = itemView.findViewById(R.id.cart_item_price);
        image_cart_count = itemView.findViewById(R.id.cart_item_count);
    }

    public void setText_cart_name(TextView text_cart_name) {
        this.text_cart_name = text_cart_name;
    }

    @Override
    public void onClick(View view) {

    }

}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {
    private List<Order> listData = new ArrayList<>();
    private Context context;


    public CartAdapter(List<Order> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_layout, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        TextDrawable txtDrawable = TextDrawable.builder()
                .buildRound(""+listData.get(position).getQuantity(), Color.RED);
        holder.image_cart_count.setImageDrawable(txtDrawable);
        Locale locals = new Locale("en","US");
        NumberFormat fat = NumberFormat.getCurrencyInstance(locals);
        int price = (Integer.parseInt(listData.get(position).getPrice()))*(Integer.parseInt(listData.get(position).getQuantity()));

        holder.text_cart_price.setText(fat.format(price));
        holder.text_cart_name.setText(listData.get(position).getProductname());


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}


