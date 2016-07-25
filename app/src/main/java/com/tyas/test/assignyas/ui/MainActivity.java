package com.tyas.test.assignyas.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.tyas.test.assignyas.R;
import com.tyas.test.assignyas.repository.entity.Data;
import com.tyas.test.assignyas.repository.service.local.DatabaseHandler;
import com.tyas.test.assignyas.repository.service.server.GetService;

import java.util.List;


public class MainActivity extends AppCompatActivity implements GetService.LoadPattern {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading data ... ");
        progressDialog.show();

        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override public void run() {
                GetService.getInstance().getAll(MainActivity.this);
            }
        }, 1000);
    }

    public void onDataButtonClick(View view) {
        startActivity(new Intent(this, DataActivity.class));
    }

    @Override public void onLoadSuccess(List<Data> datas) {
        progressDialog.dismiss();
        DatabaseHandler db = new DatabaseHandler(this);
        for (Data data : datas) {
            db.addData(data);
        }
    }

    @Override public void onLoadFailed(String message) {
        progressDialog.dismiss();
        Toast.makeText(this, "Load Failed : " + message, Toast.LENGTH_SHORT).show();
    }
}
