package com.example.cryptos.activity.main.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    private ArrayList<Integer> price = new ArrayList<>();
    private ArrayList<String> crypt = new ArrayList<>();
    private long totalbalance;
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
        TextView balancetextView = view.findViewById(R.id.balanceIdr);
        Button depowdButton = view.findViewById(R.id.depowd);
        ListView listView = view.findViewById(R.id.listwallet);

        AccountDatabase account = new AccountDatabase(context);
        String username = account.isLoggedIn();

        getprice();
        FirebaseDatabase.getInstance().getReference("/userid-"+username).child("wallet").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                ArrayList<Wallet> wallet = new ArrayList<>();
                int intidr = task.getResult().child("idr").getValue(Integer.class);
                double doubleidr = task.getResult().child("idr").getValue(Double.class);
                Wallet idr = new Wallet("IDR",doubleidr,intidr);
                wallet.add(idr);
                totalbalance =intidr;
                task.getResult().getChildren().forEach(coin -> {
                    int si = crypt.size();
                    for (int a =0; a<=si-1;a++) {
                        if (coin.getKey().equals(crypt.get(a))) {
                            double bal = coin.getValue(Double.class);
                            int bala = (int) bal;
                            int est= bala * price.get(a);
                            Wallet c = new Wallet(coin.getKey(), coin.getValue(Double.class),est);
                            wallet.add(c);
                            totalbalance+=est;
                        }
                    }
                });
                balancetextView.setText("Total Balance \n"+formatToIDR(totalbalance));
                WalletListAdapter adapter = new WalletListAdapter(context, R.layout.format_wallet, wallet);
                listView.setAdapter(adapter);
            }
        });
        depowdButton.setOnClickListener(v ->
                startActivity(
                        new Intent(context, IdrActivity.class)
                                .putExtra("username", username))
        );
        return view;
    }
    public void getprice(){
        FirebaseDatabase.getInstance().getReference("/crypto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                price.clear();
                for (DataSnapshot dt: snapshot.getChildren()) {
                    price.add(Integer.parseInt(dt.getValue().toString()));
                    crypt.add(dt.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public String formatToIDR(long estidr) {
        @SuppressLint("DefaultLocale") String p = String.valueOf(estidr);
        String formated = "";
        for (int i = p.length() - 1, j = 0; i >= 0; i--, j++) {
            if (j % 3 == 0 && i != p.length() - 1)
                formated = "." + formated;
            formated = p.charAt(i) + formated;
        }
        return "Rp. " + formated;
    }
}