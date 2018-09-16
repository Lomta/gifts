package com.mariami.lomtadze.dedac;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Fragment_Gadaricxvebi extends Fragment {

    public ArrayAdapter companyAdapter; //კომპანიების ლისტის ადაპტერი

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gadaricxvebi, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView companyList = view.findViewById(R.id.company_listview);
        companyAdapter = new CompanyListAdapter(getActivity().getApplicationContext(), R.layout.company_list, MainActivity.Companies);
        companyList.setAdapter(companyAdapter);
        companyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity().getApplicationContext(), MainActivity.Companies.get(i).getName() + i, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity().getApplicationContext(), Activity_SendGift.class);
                //გადავაწოდოთ კომპანია
                intent.putExtra("Company",MainActivity.Companies.get(i).getName());
                //გადავიდეს გადახდის გვერდზე
                MainActivity.lockUpdate=true;
                startActivity(intent);
            }
        });
    }
}
