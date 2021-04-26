package com.example.cryptos.activity.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.cryptos.R;
import com.example.cryptos.model.Wallet;

import java.util.List;

public class WalletListAdapter extends ArrayAdapter<Wallet> {
    private final Context mContext;
    int mResource;

    public WalletListAdapter(Context context, int resource, List<Wallet> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView nameTextView = convertView.findViewById(R.id.lv_wallet_crypto_name);
        TextView balanceTextView = convertView.findViewById(R.id.lv_wallet_crypto_balance);

        nameTextView.setText(getItem(position).getName().toUpperCase());
        if (!getItem(position).getName().equals("IDR")) {
            @SuppressLint("DefaultLocale") String balanceTxt =
                    String.format("%.9f", getItem(position).getCryptobalance())
                            + "\n\n" + formatToIDR(getItem(position).getIdrbalance());
            balanceTextView.setText(balanceTxt);
        } else {
            String balanceTxt = "\n" + formatToIDR(getItem(position).getIdrbalance()) + "\n";
            balanceTextView.setText(balanceTxt);
        }

        return convertView;
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
