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
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.tyas.test.assignyas.R;
import com.tyas.test.assignyas.repository.entity.Data;
import com.tyas.test.assignyas.repository.service.local.DatabaseHandler;
import com.tyas.test.assignyas.repository.service.server.GetService;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements GetService.LoadPattern {

    private ProgressDialog progressDialog;
    private List<Data> datas = new ArrayList<>();
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading data ... ");
        progressDialog.show();

        db = new DatabaseHandler(this);
        GetService.getInstance().getAll(this);
    }

    public void onDataButtonClick(View view) {
        startActivity(new Intent(this, DataActivity.class));
    }

    @Override public void onLoadSuccess(List<Data> datas) {
        this.datas.addAll(datas);
        progressDialog.dismiss();
        for (Data data : datas) {
            db.addData(data);
        }
    }

    @Override public void onLoadFailed(String message) {
        progressDialog.dismiss();
        Toast.makeText(this, "Load Failed : " + message, Toast.LENGTH_SHORT).show();
    }

    @Override public void onBackPressed() {
        if (!datas.isEmpty()) {
            for (Data data : datas) {
                db.deleteData(data);
            }

            finish();
        } else {
            super.onBackPressed();
        }

    }
}
