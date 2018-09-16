package com.mariami.lomtadze.dedac;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;

public class Activity_SendGift extends AppCompatActivity {
    EditText sUserId;
    TextView sUser;
    TextView sCompany;
    EditText sAmount;
    Button sSend;
    DataSnapshot user = MainActivity.getUserReferance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending);
        final String Company = getIntent().getStringExtra("Company");

        sUserId = findViewById(R.id.sUserId);
        sUser = findViewById(R.id.sUser);
        sCompany = findViewById(R.id.sCompany);
        sAmount = findViewById(R.id.sAmount);
        sSend = findViewById(R.id.sButton);

        sCompany.setText(Company);
        sUserId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    String userId = sUserId.getText().toString();
                    if (MainActivity.Database.child("Users").hasChild(userId)) {
                        String Name = String.valueOf(user.child("FirstName").getValue()) +
                                " " + String.valueOf(user.child("LastName").getValue());
                        sUser.setText(Name);
                    } else
                        Toast.makeText(getApplicationContext(), "მომხმარებელი არ მოიძებნა", Toast.LENGTH_LONG).show();
                }
            }
        });
        sSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String send = sAmount.getText().toString();
                BigDecimal minus = new BigDecimal(send);
                BigDecimal nw = new BigDecimal(String.valueOf(user.child("Balance").getValue()));
                nw = nw.subtract(minus);
                DatabaseReference userSelfRef = FirebaseDatabase.getInstance().getReference()
                        .child("Users").child(MainActivity.UserId);
                userSelfRef.child("Balance").setValue(String.valueOf(nw));

                DatabaseReference userOtherRef = FirebaseDatabase.getInstance().getReference()
                        .child("Users").child(sUserId.getText().toString());
                SimpleDateFormat formatServer = new SimpleDateFormat("yyyyMMddhhmmss");
                DatabaseReference giftRef = userOtherRef.child("Gifts").child(formatServer.format(new Date()));
                giftRef.child("Company").setValue(Company);
                giftRef.child("Amount").setValue(String.valueOf(minus));
                finish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainActivity.lockUpdate=false;
    }
}
