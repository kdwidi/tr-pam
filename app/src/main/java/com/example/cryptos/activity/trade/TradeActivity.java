package com.example.cryptos.activity.trade;

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
import com.google.firebase.database.FirebaseDatabase;

public class TradeActivity extends AppCompatActivity {

    TextView coin_name, coin_price, coin_balance;
    Button btn_sell, btn_buy;
    EditText total_idr;
    int doge = 5000, coin_doge = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade);
        Context context = getApplicationContext();

        AccountDatabase account = new AccountDatabase(context);
        String username = account.isLoggedIn();


        coin_name = findViewById(R.id.coin_name);
        coin_price = findViewById(R.id.coin_price);
        total_idr = findViewById(R.id.total_idr);
        btn_buy = findViewById(R.id.btn_buy);
        btn_sell = findViewById(R.id.btn_sell);


        Intent intent = getIntent();
        String coin = intent.getStringExtra("Name");
        Double price = intent.getDoubleExtra("Price", 0.00);
        System.out.println(coin);
        System.out.println(price);
        coin_name.setText(coin);
        coin_price.setText(price + "");


        //Set text total coin


        btn_buy.setOnClickListener(v -> {
            Double total_coin[] = {null};
            int saldo[] = {0};
            FirebaseDatabase.getInstance().getReference("/userid-" + username)
                    .child("wallet").get().addOnCompleteListener(task -> {
                total_coin[0] = task.getResult().child(coin).getValue(Double.class);
                saldo[0] = task.getResult().child("idr").getValue(Integer.class);
                double idr = Double.parseDouble(total_idr.getText().toString());
                System.out.println(idr);
                if (saldo[0] < idr) {
                    Toast.makeText(context, "Insuficient Balance", Toast.LENGTH_SHORT).show();
                    return;
                }
                double total_buy = idr / price;
                double total_saldo;
                System.out.println("aaaa = " + total_coin[0]);

                if (total_coin[0] != null) {

                    total_saldo = total_buy + total_coin[0];
                    System.out.println(total_saldo);
                    System.out.println(total_buy);
                    System.out.println(total_coin[0]);


                } else {
                    total_saldo = total_buy;
                    System.out.println(total_saldo);

                }
                FirebaseDatabase.getInstance().getReference("/userid-" + username + "/wallet/" + coin)
                        .setValue(total_saldo);
                Toast.makeText(context, "Transaction Succesfull", Toast.LENGTH_SHORT).show();

            });


        });

        btn_sell.setOnClickListener(v -> {
            Double total_coin[] = {null};
            int saldo[] = {0};
            FirebaseDatabase.getInstance().getReference("/userid-" + username)
                    .child("wallet").get().addOnCompleteListener(task -> {
                total_coin[0] = task.getResult().child(coin).getValue(Double.class);
                saldo[0] = task.getResult().child("idr").getValue(Integer.class);
                double idr = Double.parseDouble(total_idr.getText().toString());
                System.out.println(idr);
                double total_sell = idr / price;


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

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.buy_container, fragment)
                .commit();
    }
}
