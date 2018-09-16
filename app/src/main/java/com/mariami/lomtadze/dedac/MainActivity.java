package com.mariami.lomtadze.dedac;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    //ლოგინის დროს მიღებული რესპონსი https://api.fintech.ge/Help/Api/GET-api-Clients-Login-username-password
    private JSONObject userDetails;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = firebaseDatabase.getReference();
    public static DataSnapshot Database;
    public static String UserId = "60002222222";
    public static ArrayList<CompanyInfo> Companies = new ArrayList<>();
    public static ArrayList<CompanyInfo> Gifts = new ArrayList<>();

    //თუ მთავარ ვიუზე არ ხარ აფდეითი არ უნდა მოხდეს
    public static boolean lockUpdate=false;

    private int selectedFragmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectedFragmentId = R.id.navigation_home;
        loadFragment(new Fragment_Home());
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        for (int i = 0; i < 100; i++) {
            Companies.add(new CompanyInfo());
        }

        //უზერის დატას აღება ლოგინის ინტენტიდან
        String JsonString=getIntent().getStringExtra("APIResponse");
        try {
            userDetails = new JSONObject(JsonString);
            getDataFromFirebase();
            //userId = userDetails.getString("UserId");
            
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "ვერ მოხერხდა ბაზასთან დაკავშირება", Toast.LENGTH_LONG).show();

            //finish();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        selectedFragmentId = item.getItemId();
        return loadFragment(fragmentFromID(selectedFragmentId));
    }

    private Fragment fragmentFromID(Integer id) {
        Fragment answ = null;
        switch (id) {
            case R.id.navigation_home:
                answ = new Fragment_Home();
                break;
            case R.id.navigation_amonaweri:
                answ = new Fragment_Amonaweri();
                break;
            case R.id.navigation_baratebi:
                answ = new Fragment_Baratebi();
                break;
            case R.id.navigation_gadaxdebi:
                answ = new Fragment_Gadaricxvebi();
                break;
            case R.id.navigation_meti:
                answ = new Fragment_Meti();
                break;
        }
        return answ;
    }
    private boolean loadFragment(Fragment fragment) {
        //შეცვალე ფრაგმენტი
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager()
                    .beginTransaction();
            ft.replace(R.id.fragment_container, fragment);
            ft.commit();

            return true;
        }
        return false;
    }

    private void getDataFromFirebase() {
        Log.w("Testebi", "დაიწყო ფაიერბეიზთან კავშირი");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Database = dataSnapshot;
                Log.w("Testebi", "ბაზა ჩაიტვირთა");


                Companies.clear();
                for (DataSnapshot dataChild : Database.child("Companies").getChildren()) {
                    String Name = dataChild.getKey(); // კომპანიის სახელი
                    String Id = String.valueOf(dataChild.child("Id").getValue());
                    String LogoUrl = String.valueOf(dataChild.child("Image").getValue());
                    String Category = String.valueOf(dataChild.child("Category").getValue());
                    String Description = String.valueOf(dataChild.child("Description").getValue());
                    String New = String.valueOf(dataChild.child("New").getValue());
                    Companies.add(new CompanyInfo(Name, Id, LogoUrl, Category, Description, New));
                }

                Gifts.clear();
                for (DataSnapshot dataChild : getUserReferance().child("Gifts").getChildren()){
                    String Name = String.valueOf(dataChild.child("Company").getValue()); // კომპანიის სახელი
                    DataSnapshot companyRef = Database.child("Companies").child(Name);

                    String Id = String.valueOf(companyRef.child("Id").getValue());
                    String LogoUrl = String.valueOf(companyRef.child("Image").getValue());
                    String Category = String.valueOf(companyRef.child("Category").getValue());
                    String Description = String.valueOf(companyRef.child("Description").getValue());
                    String New = String.valueOf(dataChild.child("Amount").getValue())+" ₾";
                    Gifts.add(new CompanyInfo(Name, Id, LogoUrl, Category, Description, New));
                }
                if (!lockUpdate) loadFragment(fragmentFromID(selectedFragmentId));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Testebi", databaseError.getMessage());
            }
        });
    }

    public static DataSnapshot getUserReferance(){
        return MainActivity.Database.child("Users").child(MainActivity.UserId);
    }

}
