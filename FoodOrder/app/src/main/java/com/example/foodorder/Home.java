package com.example.foodorder;

import android.content.Intent;
import android.os.Bundle;

import com.example.foodorder.ViewHolder.FoodViewHolder;
import com.example.foodorder.ViewHolder.MenuViewHolder;
import com.example.foodorder.common.Common;
import com.example.foodorder.interfaces.ItemClickListener;
import com.example.foodorder.models.Category;
import com.example.foodorder.models.Food;
import com.example.foodorder.services.ListenOrders;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference category;
    TextView txtFullName;
    RecyclerView recycle_menu;
    FirebaseAuth mAuth;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);
        firebaseDatabase = FirebaseDatabase.getInstance();
        category = firebaseDatabase.getReference("Category");
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent ct = new Intent(Home.this,CartActivity.class);
                startActivity(ct);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        txtFullName = header.findViewById(R.id.textfull);
        txtFullName.setText(Common.currentUser.getName());
        recycle_menu = (RecyclerView)findViewById(R.id.recycle_menu);
        recycle_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycle_menu.setLayoutManager(layoutManager);
        loadMenu();
        Intent service  = new Intent(Home.this, ListenOrders.class);
        startService(service);


    }

    private void loadMenu() {
//        FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class, R.layout.menu_item,MenuViewHolder.class,category) {
//            @Override
//            protected void onBindViewHolder(@NonNull MenuViewHolder menuViewHolder, int i, @NonNull Category category) {
//
//            }
//
//            @NonNull
//            @Override
//            public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                return null;
//            }
//
//        };
       adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,R.layout.menu_item,MenuViewHolder.class,category) {
           @Override
           protected void populateViewHolder(MenuViewHolder menuViewHolder, Category category, int i) {
                    menuViewHolder.txtMenuName.setText(category.getName());
               Picasso.get().load(category.getImage())
                       .into(menuViewHolder.imgView);
               final Category clickItem = category;
               menuViewHolder.setItemClickListener(new ItemClickListener() {
                   @Override
                   public void onClick(View view, int position, boolean isLongClick) {
                     //  Toast.makeText(getApplicationContext(),""+clickItem.getImage(),Toast.LENGTH_SHORT).show();
                       Intent foodList = new Intent(Home.this,FoodListActivity.class);
                       foodList.putExtra("CategoryId",adapter.getRef(position).getKey().toString());
                       startActivity(foodList);

                   }
               });
           }
       };
            recycle_menu.setAdapter(adapter);

        }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action


            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        else
            if(id == R.id.nav_log_out){
                signOut();
                Intent inte = new Intent(Home.this,SignIn.class);
                inte.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(inte);


            }
            else
            if(id == R.id.nav_order){
                  Intent orders = new Intent(Home.this,OrderStatusActivity.class);
                  startActivity(orders);

            }

            else
                if(id == R.id.nav_cart){
                    Intent cart = new Intent(Home.this,CartActivity.class);
                    startActivity(cart);

                }

        return false;
    }

    private void signOut() {
        mAuth.signOut();
    }
}
