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
        TextView balanceinIdrTextView = convertView.findViewById(R.id.lv_wallet_crypto_balance_in_idr);

        nameTextView.setText(getItem(position).getName().toUpperCase());
        balanceTextView.setText(formatToString(getItem(position).getCryptobalance()));
        balanceinIdrTextView.setText(formatToIDR(getItem(position).getIdrbalance()));

        return convertView;
    }

    public String formatToString(double cryptobalance) {
        @SuppressLint("DefaultLocale") String p = Double.toString(cryptobalance);
        return p;
    }

    public String formatToIDR(int estidr) {
        @SuppressLint("DefaultLocale") String p = String.valueOf(estidr);
        String formated = "";
        for (int i = p.length() - 1, j = 0; i >= 0; i--, j++) {
            if (j % 3 == 0 && i != p.length() - 1)
                formated = "." + formated;
            formated = p.charAt(i) + formated;
        }
        return "Rp. " + formated;
    }
}
