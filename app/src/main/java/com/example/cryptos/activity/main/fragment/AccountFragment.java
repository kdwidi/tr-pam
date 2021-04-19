package com.example.cryptos.activity.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.cryptos.R;
import com.example.cryptos.activity.account.AccountActivity;
import com.example.cryptos.activity.main.MainActivity;
import com.example.cryptos.dao.AccountDatabase;

public class AccountFragment extends Fragment {

    public static int ACCOUNT_REQ_CODE = 101;

    public AccountFragment() {
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
        AccountDatabase account = new AccountDatabase(context);
        String username = account.isLoggedIn();
        System.out.println("Username: " + username);
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
        }
        return view;
    }
}