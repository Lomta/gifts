package com.mariami.lomtadze.dedac;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.net.URL;
import java.util.ArrayList;

public class CompanyListAdapter extends ArrayAdapter<CompanyInfo> {

    ArrayList<CompanyInfo> Companies = new ArrayList<CompanyInfo>();
    Context mContext;

    public CompanyListAdapter(Context context, int resource) {
        super(context, resource);
        mContext = context;
    }

    public CompanyListAdapter(Context context, int resource, ArrayList<CompanyInfo> items) {
        super(context, resource, items);
        Companies = items;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.company_list, null);
        }

        TextView tName = v.findViewById(R.id.tName);
        TextView tCategory = v.findViewById(R.id.tCategory);
        TextView tDescription = v.findViewById(R.id.tDescription);
        TextView tNew = v.findViewById(R.id.tNew);
        ImageView CompanyImage = v.findViewById(R.id.list_image);

        tName.setText(Companies.get(position).getName());
        tCategory.setText(Companies.get(position).getCategory());
        tDescription.setText(Companies.get(position).getDescription());
        tNew.setText(Companies.get(position).getNew());

        String logoURL = Companies.get(position).getLogoUrl();


        if (logoURL.equals("")) {
            Resources res = mContext.getResources();
            RoundedBitmapDrawable src = RoundedBitmapDrawableFactory.create(res, BitmapFactory.decodeResource(res, R.drawable.loggg));
            CompanyImage.setImageDrawable(src);
        }
        return v;
    }

}
