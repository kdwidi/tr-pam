package com.example.cryptos.activity.idr.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.cryptos.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;

public class DepositFragment extends Fragment {
    private String idrPath;
    private double balance;

    public DepositFragment(String idrPath, double balance) {
        this.idrPath = idrPath;
        this.balance = balance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = getActivity().getApplicationContext();
        View view = inflater.inflate(R.layout.fragment_deposit, container, false);
        Button depositBtn = view.findViewById(R.id.dp_button);
        TextInputEditText dpAmount = view.findViewById(R.id.deposit_amount_input);
        depositBtn.setOnClickListener(l -> {
            if (!dpAmount.getText().toString().isEmpty()) {
                double amount = Double.parseDouble(dpAmount.getText().toString());
                if (amount < 25000f) {
                    Toast.makeText(context, "Minimal deposit amount is Rp. 25.000", Toast.LENGTH_SHORT).show();
                    dpAmount.requestFocus();
                    return;
                }
                try {
                    FirebaseDatabase.getInstance().getReference(idrPath).setValue(balance + amount);
                    Toast.makeText(context, "Deposit success.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context,
                            "Failed contacting server, check your network connection.",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Fill out your account number.", Toast.LENGTH_SHORT).show();
                dpAmount.requestFocus();
            }
        });
        return view;
    }
}