package com.example.foodorder;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorder.Databases.Databases;
import com.example.foodorder.ViewHolder.CartAdapter;
import com.example.foodorder.common.Common;
import com.example.foodorder.models.Order;
import com.example.foodorder.models.Requests;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference requests;
    TextView totalText;
    Button placeOrder;
    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;
    String currentDate,currentTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");
        recyclerView = findViewById(R.id.recycle_cart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        totalText = findViewById(R.id.total);
        placeOrder = findViewById(R.id.place_order);
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showAlerts();

            }
        });
        loadlstFood(); //loa
    }

    private void showAlerts() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(CartActivity.this);
        alertDialog.setTitle("One more step");
        alertDialog.setMessage("Enter your address...");
        final EditText edtAddress = new EditText(CartActivity.this);
        LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        edtAddress.setLayoutParams(layoutParams);
        if(edtAddress.getText().length() == 0){
            edtAddress.setError("Please enter your location ....");
        }
        alertDialog.setView(edtAddress);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(edtAddress.getText().length() == 0){
                    edtAddress.setError("Please enter your location ....");
                }
                Calendar calForDate = Calendar.getInstance();
                SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM dd,yyyy");
                currentDate = currentDateFormat.format(calForDate.getTime());

                Calendar calForTime = Calendar.getInstance();
                SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm a");
                currentTime = currentTimeFormat.format(calForTime.getTime());
                Requests request = new Requests(Common.currentUser.getPhone(),Common.currentUser.getName(),edtAddress.getText().toString(),totalText.getText().toString(),currentDate,currentTime,cart);
                requests.child(String.valueOf(System.currentTimeMillis())).setValue(request);
                //clean cart
               new Databases(getBaseContext()).clearCart();
                Toast.makeText(getApplicationContext(),"Thank you your Order has been placed..",Toast.LENGTH_LONG).show();
                finish();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
alertDialog.show();
        //place order to firebase database;



    }

    private void loadlstFood() {
        cart = new Databases(this).getCart();
        adapter = new CartAdapter(cart,this);
        recyclerView.setAdapter(adapter);

        int total = 0;
        for(Order order : cart) {
            total += (Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));

        }
            Locale locals = new Locale("en","US");
            NumberFormat fat = NumberFormat.getCurrencyInstance(locals);
            totalText.setText(fat.format(total));

        }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(CartActivity.this,"Cart has been cleared for closing app add please again",Toast.LENGTH_LONG).show();
        new Databases(getBaseContext()).clearCart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(CartActivity.this,"Cart has been cleared for closing app add please again",Toast.LENGTH_LONG).show();
        new Databases(getBaseContext()).clearCart();


    }
}

