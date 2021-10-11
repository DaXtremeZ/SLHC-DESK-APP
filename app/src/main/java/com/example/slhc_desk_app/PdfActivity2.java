package com.example.slhc_desk_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class PdfActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf2);

        PDFView pdfView = findViewById(R.id.pdfview2);

        pdfView.fromAsset("Lost_passport.pdf").load();
    }
}