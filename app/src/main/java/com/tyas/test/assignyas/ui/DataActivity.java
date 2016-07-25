package com.tyas.test.assignyas.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.tyas.test.assignyas.R;
import com.tyas.test.assignyas.repository.entity.Data;
import com.tyas.test.assignyas.repository.service.local.DatabaseHandler;
import com.tyas.test.assignyas.ui.adapter.DataAdapter;

import java.util.ArrayList;
import java.util.List;

public class DataActivity extends AppCompatActivity implements DatabaseHandler.SQLiteLoadPattern {

    private DataAdapter dataAdapter;
    private List<Data> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Title List");
        if (getSupportActionBar() !=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        DatabaseHandler db = new DatabaseHandler(this);
        db.getAllData(this);

        dataAdapter = new DataAdapter(datas);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_home);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(dataAdapter);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    @Override public void onLoadSuccess(List<Data> datas) {
        this.datas.addAll(datas);
        dataAdapter.notifyDataSetChanged();
    }

    @Override public void onLoadFailed(String message) {
        Toast.makeText(this, "Load Failed : " + message, Toast.LENGTH_SHORT).show();
    }
}
