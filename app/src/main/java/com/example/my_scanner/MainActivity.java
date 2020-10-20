package com.example.my_scanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button scan,bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scan=findViewById(R.id.scann);
        bar=findViewById(R.id.bar);
        scan.setOnClickListener(this);
        bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Barcode.class));
            }
        });

    }

    @Override
    public void onClick(View v) {
        scanCode();

    }

    private void scanCode() {
        IntentIntegrator intentIntegrator=new IntentIntegrator(this);
        intentIntegrator.setCaptureActivity(Cap_Act.class);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setPrompt("Scanning..");
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult result=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result!=null) {
            if (result.getContents()!=null) {
                AlertDialog.Builder build=new AlertDialog.Builder(this);
                build.setMessage(result.getContents());
                build.setTitle("Scanning Result");
                build.setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        scanCode();

                    }
                }).setNegativeButton("Cancel Scan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }
                });
                AlertDialog dialog=build.create();
                dialog.show();

            }
            else {
                Toast.makeText(this, "No result", Toast.LENGTH_SHORT).show();
            }

        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }


    }
}