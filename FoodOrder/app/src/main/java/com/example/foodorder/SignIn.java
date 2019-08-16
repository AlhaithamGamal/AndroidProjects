package com.example.foodorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodorder.common.Common;
import com.example.foodorder.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {
    EditText edtPhone, edtPassword;
    Button btnSignIn;
    TextView forgetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        edtPhone = findViewById(R.id.edtphone);
        forgetPassword = findViewById(R.id.forget_password);
        edtPassword = findViewById(R.id.edtpassword);
        btnSignIn = findViewById(R.id.btnSignin);
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference();
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = edtPhone.getText().toString();
                String password = edtPassword.getText().toString();
                if (phone.length() == 0) {

                    edtPhone.setError("Please Enter Your Phone...");
                }

                if (password.length() == 0) {
                    edtPassword.setError("Please Enter Your Password...");

                } else {
                    final ProgressDialog progressDialog = new ProgressDialog(SignIn.this);
                    progressDialog.setMessage("Please waiting...");
                    progressDialog.show();

                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                           // String currentUserId  = mAuth.getCurrentUser().getUid();
                            if (dataSnapshot.child("User").child("currentUserId").child(edtPhone.getText().toString()).exists()) {
                                progressDialog.dismiss();
                             final  User userF = dataSnapshot.child("User").child("currentUserId").child(edtPhone.getText().toString()).getValue(User.class);
                               //System.out.println(userF.getEmail());
                               final String emails = userF.getEmail().toString().trim();
                               userF.setPhone(edtPhone.getText().toString()); //save phone in class for ordering food later
                               Toast.makeText(getApplicationContext(),"Email is:"+emails,Toast.LENGTH_LONG).show();
                                mAuth.signInWithEmailAndPassword(emails, userF.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            FirebaseUser user = mAuth.getCurrentUser();

                                                progressDialog.dismiss();
                                                if (userF.getPassword().equals(edtPassword.getText().toString())) {
                                                    Toast.makeText(getApplicationContext(), "Signed In Successfully", Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(SignIn.this,Home.class);
                                                    Common.currentUser = userF;
                                                    startActivity(intent);
                                                    finish();



                                                } else {

                                                    Toast.makeText(getApplicationContext(), "Signed In Failed", Toast.LENGTH_LONG).show();
                                                }



                                        } else {
                                            progressDialog.dismiss();
                                            Toast.makeText(getApplicationContext(), "Failed Sign In try again ...with email "+emails + task.getException(), Toast.LENGTH_LONG).show();
                                        }
                                    }

                                });


                            } else {
                                progressDialog.dismiss();

                                Toast.makeText(getApplicationContext(), "User not exists", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    });
                }


            }
        });
    }

    public void forgetPassword(View view) {
        Intent intent = new Intent(this,ForgetPassword.class);
        startActivity(intent);
    }
}
