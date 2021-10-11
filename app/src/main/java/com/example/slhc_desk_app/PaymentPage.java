package com.example.slhc_desk_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.braintreepayments.cardform.view.CardForm;

import java.util.Objects;

public class PaymentPage extends AppCompatActivity {

    CardForm cardForm;
    Button buy;
    AlertDialog.Builder alertBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);

        cardForm = findViewById(R.id.card_form);
        buy = findViewById(R.id.btnBuy);
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("SMS is required on this number")
                .setup(PaymentPage.this);
        cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        buy.setOnClickListener(view -> {
            if (cardForm.isValid()) {
                alertBuilder = new AlertDialog.Builder(PaymentPage.this);
                alertBuilder.setTitle("Confirm before purchase");
                alertBuilder.setMessage("Card number: " + cardForm.getCardNumber() + "\n" +
                        "Card expiry date: " + Objects.requireNonNull(cardForm.getExpirationDateEditText().getText()).toString() + "\n" +
                        "Card CVV: " + cardForm.getCvv() + "\n" +
                        "Postal code: " + cardForm.getPostalCode() + "\n" +
                        "Phone number: " + cardForm.getMobileNumber());
                alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Toast.makeText(PaymentPage.this, "Thank you for purchase", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(PaymentPage.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = alertBuilder.create();
                alertDialog.show();

            } else {
                Toast.makeText(PaymentPage.this, "Please complete the form", Toast.LENGTH_LONG).show();
            }
        });
    }
}
