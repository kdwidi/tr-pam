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
import com.example.cryptos.model.Crypto;

import java.util.List;

public class CryptoListAdapter extends ArrayAdapter<Crypto> {
    private final Context mContext;
    int mResource;


    public CryptoListAdapter(Context context, int resource, List<Crypto> objects) {
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

        TextView nameTextView = convertView.findViewById(R.id.lv_crypto_name);
        TextView priceTextView = convertView.findViewById(R.id.lv_crypto_price);

        nameTextView.setText(getItem(position).getName().toUpperCase());
        priceTextView.setText(formatToIDR(getItem(position).getPrice()));

        return convertView;
    }

    public String formatToIDR(double price) {
        @SuppressLint("DefaultLocale") String p = String.format("%.0f", price);
        String formated = "";
        for (int i = p.length() - 1, j = 0; i >= 0; i--, j++) {
            if (j % 3 == 0 && i != p.length() - 1)
                formated = "." + formated;
            formated = p.charAt(i) + formated;
        }
        return "Rp. " + formated;
    }
}
