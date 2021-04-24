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
import com.example.cryptos.activity.main.fragment.WalletFragment;
import com.example.cryptos.dao.AccountDatabase;
import com.example.cryptos.model.Crypto;
import com.example.cryptos.model.User;
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
    BottomNavigationView navigationView;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.main_bottom_nav_view);
        navigationView.setBackground(null);

        setFragment(new HomeFragment());

        navigationView.setOnNavigationItemSelectedListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.home_nav:
                            setFragment(new HomeFragment());
                            break;
                        case R.id.wallet_nav:
                            AccountDatabase account = new AccountDatabase(this);
                            String username = account.isLoggedIn();
                            if (username == null) {
                                navigationView.setSelectedItemId(R.id.account_nav);
                                break;
                            } else {
                                setFragment(new WalletFragment());
                                break;
                            }
                        case R.id.account_nav:
                            setFragment(new AccountFragment());
                            break;
                    }
                    return true;
                }
        );
    }

    @Override
    public void recreate() {
        super.recreate();
        navigationView.setSelectedItemId(R.id.home_nav);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "resultCode: " + resultCode);
        Log.d(TAG, "requestCode: " + requestCode);
        if (requestCode == AccountFragment.ACCOUNT_REQ_CODE) {
            if (resultCode == 1) {
                recreate();
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