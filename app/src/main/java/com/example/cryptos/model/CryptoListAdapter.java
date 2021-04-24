package com.example.cryptos.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cryptos.R;

import org.w3c.dom.Text;

import java.util.List;

public class CryptoListAdapter extends ArrayAdapter<Crypto> {
    private Context mContext;
    int mResource;


    public CryptoListAdapter(Context context, int resource,List<Crypto> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String name = getItem(position).getName();
        String price = getItem(position).getPrice();

        Crypto crypto= new Crypto(name,price);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView tvname = (TextView) convertView.findViewById(R.id.txtListView1);
        TextView tvprice = (TextView) convertView.findViewById(R.id.txtListView2);

        tvname.setText(name);
        tvprice.setText(price);

        return convertView;
    }
}
