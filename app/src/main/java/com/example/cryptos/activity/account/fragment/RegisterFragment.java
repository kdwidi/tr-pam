package com.example.cryptos.activity.account.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.cryptos.R;

public class RegisterFragment extends Fragment {

    public RegisterFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = getActivity().getApplicationContext();
        View view = inflater
                .inflate(R.layout.fragment_register, container, false);

        TextView loginHere = view.findViewById(R.id.register_login_text);

        loginHere.setOnClickListener(
                v -> getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.account_container, new LoginFragment())
                        .commit());

        return view;
    }
}