package com.example.foodorder;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorder.ViewHolder.OrderViewHolder;
import com.example.foodorder.common.Common;
import com.example.foodorder.models.Requests;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderStatusActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    FirebaseRecyclerAdapter<Requests,OrderViewHolder> adapter;
    DatabaseReference requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");
        recyclerView = findViewById(R.id.listorders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
//        if(getIntent() == null){
//            loadOrders(Common.currentUser.getPhone());
//        }
//        else {
//            loadOrders(getIntent().getStringExtra("userPhone"));
//
//        }
        loadOrders(Common.currentUser.getPhone());
    }

    private void loadOrders(final String phone) {
        adapter = new FirebaseRecyclerAdapter<Requests, OrderViewHolder>(Requests.class,R.layout.order_layout,OrderViewHolder.class,requests.orderByChild("phone").equalTo(phone)) {
            @Override
            protected void populateViewHolder(OrderViewHolder orderViewHolder, Requests requests, int i) {
                    orderViewHolder.orderId.setText(adapter.getRef(i).getKey());
                orderViewHolder.orderStatus.setText(Common.convertToStatus(requests.getStatus()));
                orderViewHolder.orderAddress.setText(requests.getAddress());
                orderViewHolder.orderPhone.setText(phone.toString());
                orderViewHolder.orderDate.setText(requests.getDate());
                orderViewHolder.orderTime.setText(requests.getTime());



            }
        };
        recyclerView.setAdapter(adapter);

    }


}
