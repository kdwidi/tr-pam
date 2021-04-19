package com.example.cryptos.activity.account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Window;

import com.example.cryptos.R;
import com.example.cryptos.activity.account.fragment.LoginFragment;
import com.example.cryptos.activity.account.fragment.RegisterFragment;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_account);

        String type = getIntent().getStringExtra("text");
        if(type != null)
            setFragment(new LoginFragment());
        else
            setFragment(new RegisterFragment());
    }

    protected void setFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.account_container, fragment)
                .commit();
    }
}