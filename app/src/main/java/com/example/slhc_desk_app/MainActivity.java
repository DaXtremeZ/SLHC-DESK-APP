package com.example.slhc_desk_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.stripe.android.PaymentConfiguration;

public class MainActivity extends AppCompatActivity {

    TextView bookTicket;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fAuth = FirebaseAuth.getInstance();

        Button button1 = findViewById(R.id.btn1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PdfActivity1.class);
                startActivity(intent);
            }
        });

        Button button2 = findViewById(R.id.btn2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PdfActivity2.class);
                startActivity(intent);
            }
        });

        initializeUI(); // set all id to the local variable
        eventHandlers(); // set all event handlers

    }

    void initializeUI(){

        bookTicket = findViewById(R.id.bookaticket);

    }

    void eventHandlers(){

        bookTicket.setOnClickListener(v-> v.getContext().startActivity(new Intent(v.getContext(),PaymentPage.class)));
    }

    public void logout(View view){
        // code for loging out
        fAuth.signOut();

        Toast.makeText(getApplicationContext(),"Logout sucess", Toast.LENGTH_SHORT).show();

        if(fAuth.getCurrentUser() ==null){
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        }
    }
}