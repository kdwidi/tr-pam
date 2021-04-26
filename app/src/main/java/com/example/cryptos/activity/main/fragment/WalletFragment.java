package com.example.cryptos.activity.main.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.cryptos.R;
import com.example.cryptos.activity.idr.IdrActivity;
import com.example.cryptos.activity.main.adapter.WalletListAdapter;
import com.example.cryptos.dao.AccountDatabase;
import com.example.cryptos.model.Wallet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WalletFragment extends Fragment {

    private ArrayList<Double> price = new ArrayList<>();
    private ArrayList<String> crypt = new ArrayList<>();
    private double totalbalance;
    private WalletListAdapter adapter;

    public WalletFragment() {
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
        TextView estimationTextView = view.findViewById(R.id.estimation_idr);
        ListView listView = view.findViewById(R.id.listwallet);

        AccountDatabase account = new AccountDatabase(context);
        String username = account.isLoggedIn();

        getPrice();
        FirebaseDatabase.getInstance().getReference("/userid-" + username).child("wallet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Wallet> wallet = new ArrayList<>();
                int intidr = snapshot.child("idr").getValue(Integer.class);
                double doubleidr = snapshot.child("idr").getValue(Double.class);
                Wallet idr = new Wallet("IDR", doubleidr, intidr);
                wallet.add(idr);
                totalbalance = intidr;
                for (DataSnapshot dt : snapshot.getChildren()) {
                    int si = crypt.size();
                    for (int a = 0; a <= si - 1; a++) {
                        if (dt.getKey().equals(crypt.get(a))) {
                            double bal = dt.getValue(Double.class);
                            double est = bal * price.get(a);
                            Wallet c = new Wallet(dt.getKey(), dt.getValue(Double.class), est);
                            wallet.add(c);
                            totalbalance += est;
                        }
                    }
                }
                String estimationStr = "\nEstimation IDR\n\n" + formatToIDR(totalbalance) + "\n";
                estimationTextView.setText(estimationStr);
                adapter = new WalletListAdapter(context, R.layout.format_wallet, wallet);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            if (position == 0)
                startActivity(
                        new Intent(context, IdrActivity.class)
                                .putExtra("username", username));
        });
        return view;
    }

    public void getPrice() {
        FirebaseDatabase.getInstance().getReference("/crypto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                price.clear();
                for (DataSnapshot dt : snapshot.getChildren()) {
                    price.add(dt.getValue(Double.class));
                    crypt.add(dt.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public String formatToIDR(double balance) {
        @SuppressLint("DefaultLocale") String p = String.format("%.0f", balance);
        String formated = "";
        for (int i = p.length() - 1, j = 0; i >= 0; i--, j++) {
            if (j % 3 == 0 && i != p.length() - 1)
                formated = "." + formated;
            formated = p.charAt(i) + formated;
        }
        return "Rp. " + formated;
    }
}