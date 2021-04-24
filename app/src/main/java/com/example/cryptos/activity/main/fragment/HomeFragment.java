package com.example.cryptos.activity.main.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.cryptos.R;
import com.example.cryptos.model.Crypto;
import com.example.cryptos.model.CryptoListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment {
    //Data-Data yang Akan dimasukan Pada ListView
    private String[] cryptoname = {"btc","doge","dot","eth","trx",
            "xmr","xrp"};
    private ArrayList cryptolist,cryptoprice = new ArrayList<>();

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ListView listView =(ListView) view.findViewById(R.id.listview);

        ArrayList<String> cryptoprice = new ArrayList<String>();
        FirebaseDatabase.getInstance().getReference("/crypto").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    task.getResult().getChildren().forEach(coin ->{
                            cryptoprice.add(coin.getValue().toString());
                    });
                    Crypto btc = new Crypto(cryptoname[0],cryptoprice.get(0));
                    Crypto doge = new Crypto(cryptoname[1],cryptoprice.get(1));
                    Crypto dot = new Crypto(cryptoname[2],cryptoprice.get(2));
                    Crypto eth = new Crypto(cryptoname[3],cryptoprice.get(3));
                    Crypto trx = new Crypto(cryptoname[4],cryptoprice.get(4));
                    Crypto xmr = new Crypto(cryptoname[5],cryptoprice.get(5));
                    Crypto xrp = new Crypto(cryptoname[6],cryptoprice.get(6));

                    ArrayList<Crypto> cryptolist = new ArrayList<>();
                    cryptolist.add(btc);
                    cryptolist.add(doge);
                    cryptolist.add(dot);
                    cryptolist.add(eth);
                    cryptolist.add(trx);
                    cryptolist.add(xmr);
                    cryptolist.add(xrp);
                    CryptoListAdapter adapter = new CryptoListAdapter(getActivity(),R.layout.format_listview,cryptolist);
                    listView.setAdapter(adapter);
                }
            }
        });
        return view;
    }
}