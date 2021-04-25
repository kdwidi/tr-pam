package com.example.cryptos.activity.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.cryptos.R;
import com.example.cryptos.activity.idr.IdrActivity;

public class WalletFragment extends Fragment {

    private String username;

    public WalletFragment(String username) {
        this.username = username;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = getActivity().getApplicationContext();
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);


        TextView idr = view.findViewById(R.id.idr_text);

        idr.setOnClickListener(v ->
                startActivity(
                        new Intent(context, IdrActivity.class)
                                .putExtra("username", username))
        );
        return view;
    }
}