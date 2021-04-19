package com.example.cryptos.activity.main;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.cryptos.R;
import com.example.cryptos.activity.main.fragment.AccountFragment;
import com.example.cryptos.activity.main.fragment.HomeFragment;
import com.example.cryptos.activity.main.fragment.MarketFragment;
import com.example.cryptos.activity.main.fragment.WalletFragment;
import com.example.cryptos.dao.Crypto;
import com.example.cryptos.dao.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pd;
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private User user;
    private Crypto crypto;
    private String username;
    private FragmentTransaction fragmentTransaction;

    public static int LOGIN_REQ_CODE = 1;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        BottomNavigationView navigationView = findViewById(R.id.main_bottom_nav_view);
        navigationView.setBackground(null);

        setFragment(new HomeFragment());

        navigationView.setOnNavigationItemSelectedListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.home_nav:
                            setFragment(new HomeFragment());
                            break;
                        case R.id.market_nav:
                            setFragment(new MarketFragment());
                            break;
                        case R.id.wallet_nav:
                            setFragment(new WalletFragment());
                            break;
                        case R.id.account_nav:
                            setFragment(new AccountFragment());
                            break;
                    }
                    return true;
                }
        );



        /*
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

        Account account = new Account(getApplication());

        // uji coba login
        String password = "pa55";
        databaseReference.child("userid-" + username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                // cek database
                if (account.isLoggedIn() == null) {
                    String response = task.getResult().child("password").getValue(String.class);
                    if (response == null) {
                        System.out.println("username tidak ada");
                    } else {
                        if (response.equals(password)) { // login berhasil
                            account.login(username);
                        } else {
                            System.out.println("login gagal");
                        }
                    }
                } else {
                    System.out.println("sudah login");
                }
            }
        });
        account.close();

         */
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "resultCode: " + resultCode);
        Log.d(TAG, "requestCode: " + requestCode);
        if(requestCode == LOGIN_REQ_CODE) {
            if(resultCode == 1) {
                setFragment(new AccountFragment());
            }
        }
    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragment)
                .commit();
    }

}