package com.example.foodorder;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.foodorder.Databases.Databases;
import com.example.foodorder.models.Food;
import com.example.foodorder.models.Order;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FoodDetails extends AppCompatActivity {

    TextView food_name,food_price,food_description,food_discount;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ElegantNumberButton elegantNumberButton;
    Food currentFood;
    String foodId="" ;
    private FloatingActionButton btnCart;
    FirebaseDatabase database;
    DatabaseReference foods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        database = FirebaseDatabase.getInstance();
        foods = database.getReference("Food");
        elegantNumberButton = (ElegantNumberButton)findViewById(R.id.number_button);
        food_description = findViewById(R.id.food_description);
        food_name = findViewById(R.id.food_name);
        btnCart = (FloatingActionButton)findViewById(R.id.btnCart);
        food_price = findViewById(R.id.food_price);
        food_image = findViewById(R.id.img_food);
        food_discount = findViewById(R.id.food_discount);
        collapsingToolbarLayout = findViewById(R.id.collapsint_toolbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapseAppbar);
        if(getIntent() != null)
            foodId = getIntent().getStringExtra("FoodId");
        if(!foodId.isEmpty()){

            getDetailFoodId(foodId);
        }
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Databases(getBaseContext()).addCart(new Order(
                        foodId,
                        currentFood.getName(),
                        elegantNumberButton.getNumber().toString(),
                        currentFood.getPrice(),
                        currentFood.getDiscount())
                );

                Toast.makeText(getApplicationContext(),"Added to the cart",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getDetailFoodId(String foodId) {
        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 currentFood = dataSnapshot.getValue(Food.class);
                Picasso.get().load(currentFood.getImage())
                        .into(food_image);
                collapsingToolbarLayout.setTitle(food_name.getText().toString());
                food_price.setText(String.valueOf( currentFood.getPrice()));
                food_name.setText( currentFood.getName());
                food_description.setText( currentFood.getDescription());
                food_discount.setText(currentFood.getDiscount());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
