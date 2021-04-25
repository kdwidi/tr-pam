package com.example.cryptos.activity.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.cryptos.R;
import com.example.cryptos.activity.main.adapter.CryptoListAdapter;
import com.example.cryptos.model.Crypto;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = getActivity().getApplicationContext();
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ListView listView = view.findViewById(R.id.listview);

        FirebaseDatabase.getInstance().getReference("/crypto").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                ArrayList<Crypto> crypto = new ArrayList<>();
                task.getResult().getChildren().forEach(coin -> {
                    Crypto c = new Crypto(coin.getKey(), coin.getValue(Double.class));
                    crypto.add(c);
                });
                CryptoListAdapter adapter = new CryptoListAdapter(context, R.layout.format_listview, crypto);
                listView.setAdapter(adapter);
            }
        });
        return view;
    }
}