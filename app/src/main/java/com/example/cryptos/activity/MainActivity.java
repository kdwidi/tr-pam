package com.example.cryptos.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cryptos.R;
import com.example.cryptos.model.Crypto;
import com.example.cryptos.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog pd;
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private User user;
    private Crypto crypto;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crypto = new Crypto();
        user = new User();
        username = "kadekwidi";

        pd = new ProgressDialog(this);
        pd.setMessage("Loading data...");
        pd.show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pd.dismiss();
                crypto.setCrypto(snapshot.child("crypto"));
                user.setUser(snapshot.child("userid-" + username));

                // testing
                System.out.println("===========================");
                crypto.testPrint();
                System.out.println("===========================");
                user.testPrint();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });

        // uji coba login
        String password = "pa55";
        databaseReference.child("userid-" + username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                String response =  task.getResult().child("password").getValue(String.class);
                if(response == null) {
                    System.out.println("username tidak ada");
                } else {
                    System.out.println("input: " + password);
                    System.out.println("response: " + response);
                    if(response.equals(password)) {
                        System.out.println("berhasil login");
                    } else {
                        System.out.println("login gagal");
                    }
                }
            }
        });
    }

}