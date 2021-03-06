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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cryptos.R;
import com.example.cryptos.dao.AccountDatabase;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterFragment extends Fragment {

    public RegisterFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.fragment_register, container, false);
        Context context = view.getContext();

        TextInputEditText usernameInput = view.findViewById(R.id.register_username_input);
        TextInputEditText nameInput = view.findViewById(R.id.register_name_input);
        TextInputEditText mobileInput = view.findViewById(R.id.register_mobile_number_input);
        TextInputEditText passwordInput = view.findViewById(R.id.register_password_input);
        TextInputEditText confirmPasswordInput = view.findViewById(R.id.register_confirm_password_input);
        Button registerBtn = view.findViewById(R.id.register_register_btn);
        TextView loginHere = view.findViewById(R.id.register_login_text);


        registerBtn.setOnClickListener(v -> {

            String username = usernameInput.getText().toString();

            if (username.isEmpty()) {
                usernameInput.requestFocus();
                Toast.makeText(context,
                        "Please fill all field corectly.", Toast.LENGTH_SHORT).show();
                return;
            }
            register(FirebaseDatabase.getInstance().getReference("userid-" + username).get(),
                    new RegisterUserListener() {
                        ProgressDialog pd;

                        final String name = nameInput.getText().toString();
                        final String mobile = mobileInput.getText().toString();
                        final String password = passwordInput.getText().toString();
                        final String confirmPassword = confirmPasswordInput.getText().toString();

                        @Override
                        public int onStart() {
                            pd = new ProgressDialog(context);
                            pd.setMessage("Processing...");
                            pd.show();

                            String emptyTextInputMessage = "Please fill all field correctly.";

                            if (name.isEmpty()) {
                                onCancelled(RegisterUserListener.EMPTY_TEXT_INPUT,
                                        emptyTextInputMessage, nameInput);
                                return EXIT;
                            }

                            if (mobile.isEmpty()) {
                                onCancelled(RegisterUserListener.EMPTY_TEXT_INPUT,
                                        emptyTextInputMessage, mobileInput);
                                return EXIT;
                            }

                            if (password.isEmpty()) {
                                onCancelled(RegisterUserListener.EMPTY_TEXT_INPUT,
                                        emptyTextInputMessage, passwordInput);
                                return EXIT;
                            }

                            if (confirmPassword.isEmpty()) {
                                onCancelled(RegisterUserListener.EMPTY_TEXT_INPUT,
                                        emptyTextInputMessage, confirmPasswordInput);
                                return EXIT;
                            }

                            if (!password.equals(confirmPassword)) {
                                onCancelled(0,
                                        "Password not matched.", passwordInput);
                                return EXIT;
                            }
                            return 0;
                        }

                        @Override
                        public void onSuccess() {

                            String USERNAME_PATH = "userid-" + username;
                            String NAME_PATH = USERNAME_PATH + "/name";
                            String TELP_PATH = USERNAME_PATH + "/telp";
                            String PASSWORD_PATH = USERNAME_PATH + "/password";
                            String WALLET_PATH = USERNAME_PATH + "/wallet/idr";

                            FirebaseDatabase.getInstance().getReference(NAME_PATH).setValue(name);
                            FirebaseDatabase.getInstance().getReference(TELP_PATH).setValue(mobile);
                            FirebaseDatabase.getInstance().getReference(PASSWORD_PATH).setValue(password);
                            FirebaseDatabase.getInstance().getReference(WALLET_PATH).setValue(0);

                            int resultCode;
                            AccountDatabase db = new AccountDatabase(context);
                            if (db.login(username) == 1) resultCode = 1;
                            else resultCode = -1;

                            Toast.makeText(context,
                                    "Registration success.", Toast.LENGTH_SHORT).show();

                            getActivity().setResult(resultCode);
                            getActivity().finish();

                            if (pd.isShowing())
                                pd.dismiss();
                        }

                        @Override
                        public void onCancelled(int errorCode, String message, @Nullable TextInputEditText editText) {
                            if (pd.isShowing())
                                pd.dismiss();
                            if (errorCode == -1)
                                editText.requestFocus();
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        loginHere.setOnClickListener(
                v -> getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.account_container, new LoginFragment())
                        .commit());

        return view;
    }

    void register(Task<DataSnapshot> task, RegisterUserListener listener) {
        final String[] isTaken = {""};
        int exitStatus = listener.onStart();
        if (exitStatus != listener.EXIT)
            task.addOnCompleteListener(l -> {
                try {
                    isTaken[0] = l.getResult().child("name").getValue(String.class);
                } catch (RuntimeExecutionException e) {
                    e.printStackTrace();
                    listener.onCancelled(0,
                            "Failed contacting server, check your network connection.",
                            null);
                }
                if (isTaken[0] != null) {
                    listener.onCancelled(0,
                            "Username already taken, please use another.", null);
                    return;
                }
                listener.onSuccess();
            });
    }

    interface RegisterUserListener {
        int EMPTY_TEXT_INPUT = -1;
        int EXIT = -100;

        int onStart();

        void onSuccess();

        void onCancelled(int errorCode, String message, @Nullable TextInputEditText editText);
    }
}