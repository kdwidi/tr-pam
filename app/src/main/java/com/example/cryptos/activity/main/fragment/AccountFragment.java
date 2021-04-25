package com.example.cryptos.activity.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.cryptos.R;
import com.example.cryptos.activity.about.AboutActivity;
import com.example.cryptos.activity.about.MapsActivity;
import com.example.cryptos.activity.account.AccountActivity;
import com.example.cryptos.activity.main.MainActivity;
import com.example.cryptos.dao.AccountDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

public class AccountFragment extends Fragment {

    public static int ACCOUNT_REQ_CODE = 101;
    private AccountDatabase account;

    public AccountFragment(AccountDatabase account) {
        this.account = account;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = getActivity().getApplicationContext();
        View view;
        String username = account.isLoggedIn();
        if (username == null) {
            view = inflater.inflate(
                    R.layout.fragment_account_req_login, container, false);
            Button loginBtn = view.findViewById(R.id.account_req_login_btn);
            Button registerBtn = view.findViewById(R.id.account_req_register_btn);
            loginBtn.setOnClickListener(
                    v -> getActivity().startActivityForResult(
                            new Intent(context, AccountActivity.class)
                                    .putExtra("text", "login"),
                            ACCOUNT_REQ_CODE));
            registerBtn.setOnClickListener(
                    v -> getActivity().startActivityForResult(
                            new Intent(context, AccountActivity.class),
                            ACCOUNT_REQ_CODE));
        } else {
            view = inflater.inflate(R.layout.fragment_account, container, false);
            TextView txtName = view.findViewById(R.id.name);
            TextView txtUsername = view.findViewById(R.id.username);
            TextView txtNumberphone = view.findViewById(R.id.nohp);
            Button AboutBtn = view.findViewById(R.id.about_btn);
            Button LogoutBtn = view.findViewById(R.id.logout_btn);

            txtUsername.setText(username);
            FirebaseDatabase.getInstance().getReference("/userid-"+username).getRef().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String name = (String) dataSnapshot.child("name").getValue();
                    txtName.setText(name);
                    String numberphone = (String) dataSnapshot.child("telp").getValue();
                    txtNumberphone.setText(numberphone);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Log.e(TAG, "Failed to read value.", error.toException());
                }
            });
            AboutBtn.setOnClickListener(v -> {
                startActivity(new Intent(context, AboutActivity.class));
            });
            LogoutBtn.setOnClickListener(v -> {
                account.logout();
                getActivity().recreate();
            });
        }
        return view;
    }
}