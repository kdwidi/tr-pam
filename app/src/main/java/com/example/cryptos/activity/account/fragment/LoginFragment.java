package com.example.cryptos.activity.account.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.cryptos.R;

public class LoginFragment extends Fragment {

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.fragment_login, container, false);
        TextView registerHere = view.findViewById(R.id.login_register_text);
        registerHere.setOnClickListener(
                v -> getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.account_container, new RegisterFragment())
                        .commit());
        return view;
    }
}