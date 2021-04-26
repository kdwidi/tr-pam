package com.example.cryptos.activity.trade;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cryptos.R;
import com.example.cryptos.dao.AccountDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class TradeActivity extends AppCompatActivity {

    TextView coin_name, coin_price, total_balance, balance_coin;
    Button btn_sell, btn_buy;
    EditText total_idr_buy, total_idr_sell;
    double t_coin,t_balance,balance_after_tf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_trade);
        Context context = getApplicationContext();

        AccountDatabase account = new AccountDatabase(context);
        String username = account.isLoggedIn();


        coin_name = findViewById(R.id.coin_name);
        coin_price = findViewById(R.id.coin_price);
        total_idr_buy = findViewById(R.id.total_idr_buy);
        total_idr_sell = findViewById(R.id.total_idr_sell);
        total_balance = findViewById(R.id.balance);
        balance_coin = findViewById(R.id.balance_coin);

        btn_buy = findViewById(R.id.btn_buy);
        btn_sell = findViewById(R.id.btn_sell);
        total_idr_buy.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                total_idr_buy.removeTextChangedListener(this);

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

                    total_idr_buy.setText(formattedString);
                    total_idr_buy.setSelection(total_idr_buy.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                total_idr_buy.addTextChangedListener(this);
            }
        });
        total_idr_sell.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                total_idr_sell.removeTextChangedListener(this);

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

                    total_idr_sell.setText(formattedString);
                    total_idr_sell.setSelection(total_idr_sell.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                total_idr_sell.addTextChangedListener(this);
            }
        });


        Intent intent = getIntent();
        String coin = intent.getStringExtra("Name");
        Double price = intent.getDoubleExtra("Price", 0.00);
        System.out.println(coin);
        System.out.println(price);
        coin_name.setText(coin.toUpperCase());
        coin_price.setText(formatToIDR(price));
        FirebaseDatabase.getInstance().getReference("/userid-" + username)
                .child("wallet").addValueEventListener(new ValueEventListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                t_balance = snapshot.child("idr").getValue(Double.class);
                total_balance.setText(formatToIDR(t_balance));
                for (DataSnapshot crypto: snapshot.getChildren()) {
                    if(crypto.getKey().equals(coin)) {
                        t_coin = crypto.getValue(Double.class);
                        break;
                    } else {
                        t_coin = 0;
                    }
                }
                balance_coin.setText(String.format("%.9f", t_coin));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btn_buy.setOnClickListener(v -> {

            if (total_idr_buy.getText().toString().isEmpty()) {
                Toast.makeText(context,
                        "Invalid input!", Toast.LENGTH_SHORT).show();
                return;
            }
            Integer idr_buy = Integer.parseInt(total_idr_buy.getText().toString().replace(",", ""));
            balance_after_tf = t_balance - idr_buy;
            System.out.println(idr_buy);
            if (t_balance < idr_buy) {
                Toast.makeText(context, "Insufficient Balance", Toast.LENGTH_SHORT).show();
                return;
            }
            double total_buy = idr_buy / price;
            double total_coin_buy;
            System.out.println("aaaa = " + t_coin);

            if (t_coin != 0) {

                total_coin_buy = total_buy + t_coin;
                System.out.println(total_balance);
                System.out.println(total_buy);
                System.out.println(t_coin);


            } else {
                total_coin_buy = total_buy;
                System.out.println(total_coin_buy);

            }
            FirebaseDatabase.getInstance().getReference("/userid-" + username + "/wallet/" + coin)
                    .setValue(total_coin_buy);
            FirebaseDatabase.getInstance().getReference("/userid-" + username + "/wallet/" + "idr")
                    .setValue(balance_after_tf);
            Toast.makeText(context, "Transaction Succesfull", Toast.LENGTH_SHORT).show();

        });


        btn_sell.setOnClickListener(v -> {


            if (total_idr_sell.getText().toString().isEmpty()) {
                Toast.makeText(context,
                        "Invalid input!", Toast.LENGTH_SHORT).show();
                return;
            }
            Integer idr_sell = Integer.parseInt(total_idr_sell.getText().toString().replace(",", ""));
            balance_after_tf = t_balance - idr_sell;
            System.out.println(idr_sell);
            double total_sell = idr_sell / price;


            double total_saldo;
            System.out.println("aaaa = " + t_coin);

            if (t_coin != 0) {
                if (t_coin < total_sell) {
                    Toast.makeText(context, "Insuficient Balance", Toast.LENGTH_SHORT).show();
                    return;
                }

                total_saldo = t_coin - total_sell;


            } else {
                Toast.makeText(context, "Insuficient Balance", Toast.LENGTH_SHORT).show();
                return;

            }
            System.out.println("Total saldo: "+total_saldo);
            FirebaseDatabase.getInstance().getReference("/userid-" + username + "/wallet/" + coin)
                    .setValue(total_saldo);
            FirebaseDatabase.getInstance().getReference("/userid-" + username + "/wallet/" + "idr")
                    .setValue(balance_after_tf);
            Toast.makeText(context, "Transaction Succesfull", Toast.LENGTH_SHORT).show();

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
