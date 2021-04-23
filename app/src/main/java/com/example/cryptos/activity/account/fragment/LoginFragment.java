package com.example.cryptos.activity.account.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.cryptos.R;
import com.example.cryptos.activity.main.MainActivity;
import com.example.cryptos.dao.AccountDatabase;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;

public class LoginFragment extends Fragment {
    private final String TAG = LoginFragment.class.getSimpleName();

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.fragment_login, container, false);
        Context context = view.getContext();

        TextView registerHere = view.findViewById(R.id.login_register_text);
        TextInputEditText usernameInput = view.findViewById(R.id.login_username_input);
        TextInputEditText passwordInput = view.findViewById(R.id.login_password_input);
        Button loginBtn = view.findViewById(R.id.login_login_btn);

        loginBtn.setOnClickListener(v -> {
            String username = usernameInput.getText().toString();
            String password = passwordInput.getText().toString();

            ProgressDialog pd = new ProgressDialog(context);
            pd.setMessage("Logging in...");
            pd.show();

            AccountDatabase db = new AccountDatabase(context);
            FirebaseDatabase.getInstance()
                    .getReference("userid-" + username)
                    .get().addOnCompleteListener(task -> {
                String response = task.getResult().child("password").getValue(String.class);
                pd.dismiss();
                if (response == null) {
                    Toast.makeText(
                            context,
                            "Username or password invalid.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (response.equals(password)) { // login berhasil
                        int resultCode;
                        if (db.login(username) == 1) resultCode = 1;
                        else resultCode = -1;
                        getActivity().setResult(resultCode);
                        getActivity().finish();
                    } else {
                        Toast.makeText(
                                context,
                                "Username or password invalid.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                db.close();
            });
        });

        registerHere.setOnClickListener(
                v -> getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.account_container, new RegisterFragment())
                        .commit());
        return view;
    }
}