package com.mariami.lomtadze.dedac;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Fragment_Home extends Fragment {

    public ArrayAdapter giftAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.w("Testebi", "Home gamoidzaxa");

        TextView Name = view.findViewById(R.id.FragmentText);
        TextView Balance = view.findViewById(R.id.fragmentBalance);
        ListView giftList = view.findViewById(R.id.gift_Listview);
        giftAdapter = new CompanyListAdapter(getActivity().getApplicationContext(), R.layout.company_list, MainActivity.Gifts);
        giftList.setAdapter(giftAdapter);

        if (MainActivity.Database != null) {
            Name.setText(String.valueOf(MainActivity.Database.child("Users").child(MainActivity.UserId).child("FirstName").getValue()));
            String tmp = String.valueOf(MainActivity.getUserReferance().child("Balance").getValue())+"₾";
            Balance.setText(tmp);
        }


    }
}
