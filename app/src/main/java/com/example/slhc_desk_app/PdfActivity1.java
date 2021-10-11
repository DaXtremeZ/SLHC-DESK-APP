package com.example.slhc_desk_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class PdfActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf1);

        PDFView pdfView = findViewById(R.id.pdfview1);

        pdfView.fromAsset("emergency_travel.pdf").load();
    }
}