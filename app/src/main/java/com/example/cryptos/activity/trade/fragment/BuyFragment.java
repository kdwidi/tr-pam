package com.example.cryptos.activity.trade.fragment;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.cryptos.R;

public class BuyFragment extends Fragment {
    public BuyFragment() {
    }

    TextView txt1,txt2,txt3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        int[] choose = {1,2,3};




        Context context = getActivity().getApplicationContext();
        View view = inflater.inflate(R.layout.fragment_buy, container, false);
        txt1 = (TextView) view.findViewById(R.id.a1);
        txt2 = view.findViewById(R.id.a2);
        txt3 = view.findViewById(R.id.a3);







        return view;

    }
}
