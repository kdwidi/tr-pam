package com.example.cryptos.activity.idr;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cryptos.R;
import com.example.cryptos.activity.idr.fragment.DepositFragment;
import com.example.cryptos.activity.idr.fragment.WithdrawFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IdrActivity extends AppCompatActivity {
    private TextView balanceTextView;
    private double balance;
    private String IDR_PATH = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_idr);

        String username = getIntent().getExtras().getString("username");
        IDR_PATH = "/userid-" + username + "/wallet/idr";

        TabLayout tabLayout = findViewById(R.id.tab_idr_layout);
        balanceTextView = findViewById(R.id.idr_balance_text);

        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.show();

        FirebaseDatabase.getInstance().getReference(IDR_PATH).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                balance = snapshot.getValue(Double.class);
                balanceTextView.setText(formatToIdr(balance));
                setFragmentBySelectedTab(tabLayout.getSelectedTabPosition());
                if (pd.isShowing())
                    pd.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),
                        "Failed contacting server, check your network connection.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setFragmentBySelectedTab(tabLayout.getSelectedTabPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setFragmentBySelectedTab(int selectedTab) {
        Fragment fragment;
        if (selectedTab == 0) fragment = new DepositFragment(IDR_PATH, balance);
        else fragment = new WithdrawFragment(IDR_PATH, balance);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.idr_frame_layout, fragment);
        if (!fm.isDestroyed())
            ft.commit();

    }

    private String formatToIdr(double balance) {
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