package com.mariami.lomtadze.dedac;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Activity_Login extends Activity {
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username12);
        password = findViewById(R.id.password12);
        Button migeba = findViewById(R.id.shesvl);

        migeba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openmainscreen();
            }
        });

    }

    public void openmainscreen() {

        String url = "https://api.fintech.ge/api/Clients/Login/" + username.getText().toString() + "/" + password.getText().toString();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("APIResponse", response.toString());

                String name;
                JSONObject userDetails;
                try {

                    userDetails = response.getJSONObject("UserDetails");
                    name = userDetails.getString("Name");

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "შეცდომა მონაცემთა ანალიზისას", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), "მოგესალმებით " + name, Toast.LENGTH_LONG).show();


                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                //გადავაწოდოთ რესპონს ჯეისონი როგორც სტრინგი
                intent.putExtra("APIResponse",userDetails.toString());
                //გადავიდეს მთავარ გვერდზე
                startActivity(intent);
                //დაამთავროს საინინ აქტივიტი
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.w("APIResponse", error.toString());
                Toast.makeText(getApplicationContext(), "ვერ მოხერხდა ბაზასთან დაკავშირება", Toast.LENGTH_LONG).show();
            }
        });

        Volley.newRequestQueue(this).add(jsonRequest);

    }

}
