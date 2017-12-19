package com.akash.intandextstorage;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    EditText etLine;
    Button btnSaveInt,btnGetInt,btnSaveExt,btnGetExt;
    TextView tvDisplay;

    String filename = "MySampleFile.txt";
    String filepath = "MyFileStorage";
    File myInternalFile;
    File myExternalFile;
    String myData=" ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etLine = (EditText) findViewById(R.id.etLine);
        tvDisplay = (TextView) findViewById(R.id.tvDisplay);
        btnGetExt = (Button) findViewById(R.id.btnGetExt);
        btnGetInt = (Button) findViewById(R.id.btnGetInt);
        btnSaveExt = (Button) findViewById(R.id.btnSaveExt);
        btnSaveInt = (Button) findViewById(R.id.btnSaveInt);

        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File directory = contextWrapper.getDir(filepath, Context.MODE_PRIVATE);
        myInternalFile = new File(directory, filename);

        //check if external storage is available and not read only
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            btnSaveExt.setEnabled(false);
        } else {
            myExternalFile = new File(getExternalFilesDir(filepath), filename);
        }

        btnSaveInt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileOutputStream fos = new FileOutputStream(myInternalFile);
                    fos.write(etLine.getText().toString().getBytes());
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                etLine.setText("");
                tvDisplay.setText("MySampleFile.txt saved to Internal Storage...");
            }
        });

        btnGetInt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileInputStream fis = new FileInputStream(myInternalFile);
                    DataInputStream in = new DataInputStream(fis);
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String strLine;
                    myData = "";
                    while ((strLine = br.readLine()) != null) {
                        myData = myData + strLine;
                    }
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                etLine.setText("");
                tvDisplay.setText(myData);
            }
        });

        btnSaveExt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileOutputStream fos = new FileOutputStream(myExternalFile);
                    fos.write(etLine.getText().toString().getBytes());
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                etLine.setText("");
                tvDisplay.setText("MySampleFile.txt saved to External Storage...");
            }
        });

        btnGetExt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileInputStream fis = new FileInputStream(myExternalFile);
                    DataInputStream in = new DataInputStream(fis);
                    BufferedReader br =
                            new BufferedReader(new InputStreamReader(in));
                    String strLine;
                    myData="";
                    while ((strLine = br.readLine()) != null) {
                        myData = myData + strLine;
                    }
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                etLine.setText("");
                tvDisplay.setText(myData);
            }
        });
    }

        private static boolean isExternalStorageReadOnly() {
            String extStorageState = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
                return true;
            }
            return false;
        }

        private static boolean isExternalStorageAvailable() {
            String extStorageState = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
                return true;
            }
            return false;
        }
}
