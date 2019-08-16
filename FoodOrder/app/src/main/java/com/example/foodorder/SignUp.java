package com.example.foodorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class SignUp extends AppCompatActivity {
    Button signup;
    EditText phone, name, password, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signup = findViewById(R.id.btnSignup);
        phone = findViewById(R.id.edtphone);
        email = findViewById(R.id.edtemail);
        name = findViewById(R.id.edtname);
        password = findViewById(R.id.edtpassword);
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference();
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString();
                String Password = password.getText().toString();
                String Name = name.getText().toString();
                String Phone = phone.getText().toString();
                if (Email.length() == 0) {

                    email.setError("Please Enter Your Email...");
                }

                if (Password.length() == 0) {
                    password.setError("Please Enter Your Password...");

                }
                if (Phone.length() == 0) {
                    phone.setError("Please Enter Your Phone...");

                }
                if (Name.length() == 0) {
                    name.setError("Please Enter Your Password...");

                } else {
                    final ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
                    progressDialog.setTitle("Wait");
                    progressDialog.setMessage("Please waiting...");
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                FirebaseUser user = mAuth.getCurrentUser();
                                //   user.sendEmailVerification();
                                table_user.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.child(phone.getText().toString()).exists()) {

                                            progressDialog.dismiss();
                                            Toast.makeText(getApplicationContext(), "Phone exists", Toast.LENGTH_LONG).show();
                                        } else {

                                            progressDialog.dismiss();
                                            String currentUserId = mAuth.getCurrentUser().getUid();
                                            User user = new User(email.getText().toString(),name.getText().toString(), password.getText().toString());
                                            table_user.child("User").child("currentUserId").child(phone.getText().toString()).setValue(user);
                                            Toast.makeText(getApplicationContext(), "Sign up successfully", Toast.LENGTH_LONG).show();
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                            } /*else {
                                    Toast.makeText(getApplicationContext(), "Check Verification", Toast.LENGTH_LONG).show();
                                }*/ else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Failed" + task.getException(), Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                }


            }
        });


    }
}
