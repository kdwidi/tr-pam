package com.example.cryptos.activity.idr.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.cryptos.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class WithdrawFragment extends Fragment {
    private String idrPath;
    private double balance;

    public WithdrawFragment(String idrPath, double balance) {
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
        View view = inflater.inflate(R.layout.fragment_withdraw, container, false);

        Button wdBtn = view.findViewById(R.id.wd_button);
        TextInputEditText wdAmount = view.findViewById(R.id.withdraw_amount_input);
        TextInputEditText wdAccount = view.findViewById(R.id.withdraw_account_input);

        wdAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                wdAmount.removeTextChangedListener(this);

                try {
                    String awal = s.toString();
                    Long akhir;
                    if (awal.contains(",")) {
                        awal = awal.replaceAll(",", "");
                    }
                    akhir = Long.parseLong(awal);

                    DecimalFormat pattern = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    pattern.applyPattern("#,###,###,###");
                    String formattedString = pattern.format(akhir);

                    wdAmount.setText(formattedString);
                    wdAmount.setSelection(wdAmount.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                wdAmount.addTextChangedListener(this);
            }
        });

        wdBtn.setOnClickListener(l -> {
            if (wdAmount.getText().toString().isEmpty()) {
                Toast.makeText(context, "Fill out withdraw amount.", Toast.LENGTH_SHORT).show();
                wdAmount.requestFocus();
                return;
            }
            if (!wdAccount.getText().toString().isEmpty()) {
                double amount = Double.parseDouble(wdAmount.getText().toString().replace(",", ""));
                if (amount < 25000f) {
                    Toast.makeText(context, "Minimal withdraw amount is Rp. 25.000", Toast.LENGTH_SHORT).show();
                    wdAmount.requestFocus();
                    return;
                }
                if (balance >= amount)
                    try {
                        FirebaseDatabase.getInstance().getReference(idrPath).setValue(balance - amount);
                        Toast.makeText(context, "Withdraw success.", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context,
                                "Failed contacting server, check your network connection.",
                                Toast.LENGTH_SHORT).show();
                    }
                else
                    Toast.makeText(context, "Insufficient balance!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Fill out your account number.", Toast.LENGTH_SHORT).show();
                wdAccount.requestFocus();
            }
        });
        return view;
    }
}