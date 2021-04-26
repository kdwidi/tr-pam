package com.example.cryptos.activity.trade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cryptos.R;
import com.example.cryptos.activity.main.fragment.HomeFragment;
import com.example.cryptos.activity.trade.fragment.BuyFragment;
import com.example.cryptos.dao.AccountDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TradeActivity extends AppCompatActivity {

    TextView coin_name, coin_price, total_saldo;
    Button btn_sell, btn_buy;
    EditText total_idr_buy, total_idr_sell;
    int doge = 5000, coin_doge = 5;
    int t_saldo, t_coin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade);
        Context context = getApplicationContext();

        AccountDatabase account = new AccountDatabase(context);
        String username = account.isLoggedIn();


        coin_name = findViewById(R.id.coin_name);
        coin_price = findViewById(R.id.coin_price);
        total_idr_buy = findViewById(R.id.total_idr_buy);
        total_idr_sell = findViewById(R.id.total_idr_sell);
        total_saldo = findViewById(R.id.saldo);

        btn_buy = findViewById(R.id.btn_buy);
        btn_sell = findViewById(R.id.btn_sell);


        Intent intent = getIntent();
        String coin = intent.getStringExtra("Name");
        Double price = intent.getDoubleExtra("Price", 0.00);
        System.out.println(coin);
        System.out.println(price);
        coin_name.setText(coin);
        coin_price.setText(price + "");
        FirebaseDatabase.getInstance().getReference("/userid-" + username)
                .child("wallet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                t_saldo = snapshot.child("idr").getValue(Integer.class);
                total_saldo.setText(t_saldo + "");
                t_coin = snapshot.child(coin).getValue(Integer.class);
                // set text total coin

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //Set text total coin


        btn_buy.setOnClickListener(v -> {

            double idr_buy = Double.parseDouble(total_idr_buy.getText().toString());
            System.out.println(idr_buy);
            if (t_saldo < idr_buy) {
                Toast.makeText(context, "Insuficient Balance", Toast.LENGTH_SHORT).show();
                return;
            }
            double total_buy = idr_buy / price;
            double total_saldo;
            System.out.println("aaaa = " + t_coin);

            if (t_coin != 0) {

                total_saldo = total_buy + t_coin;
                System.out.println(total_saldo);
                System.out.println(total_buy);
                System.out.println(t_coin);


            } else {
                total_saldo = total_buy;
                System.out.println(total_saldo);

            }
            FirebaseDatabase.getInstance().getReference("/userid-" + username + "/wallet/" + coin)
                    .setValue(total_saldo);
            Toast.makeText(context, "Transaction Succesfull", Toast.LENGTH_SHORT).show();

        });


        btn_sell.setOnClickListener(v -> {
            Double total_coin[] = {null};
            int saldo[] = {0};
            FirebaseDatabase.getInstance().getReference("/userid-" + username)
                    .child("wallet").get().addOnCompleteListener(task -> {
                total_coin[0] = task.getResult().child(coin).getValue(Double.class);
                saldo[0] = task.getResult().child("idr").getValue(Integer.class);
                double idr_sell = Double.parseDouble(total_idr_sell.getText().toString());
                System.out.println(idr_sell);
                double total_sell = idr_sell / price;


                double total_saldo;
                System.out.println("aaaa = " + total_coin[0]);

                if (total_coin[0] != null) {
                    if (total_coin[0] < total_sell) {
                        Toast.makeText(context, "Insuficient Balance", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    total_saldo = total_coin[0] - total_sell;


                } else {
                    Toast.makeText(context, "Insuficient Balance", Toast.LENGTH_SHORT).show();
                    return;

                }
                FirebaseDatabase.getInstance().getReference("/userid-" + username + "/wallet/" + coin)
                        .setValue(total_saldo);
                Toast.makeText(context, "Transaction Succesfull", Toast.LENGTH_SHORT).show();

            });


        });


    }
}
