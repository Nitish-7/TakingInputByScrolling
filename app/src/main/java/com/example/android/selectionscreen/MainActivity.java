package com.example.android.selectionscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Litsener {

    private float middleUnitOnScale = 0;
    public int firstVisibleUnitOnScal;
    public int lastVisibleUnitOnScal;
    private MainActivityViewModel viewModel;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MainActivityViewModel.class);

        //setting ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setElevation(0);

        //setting height selector recyclerview
        recyclerView = findViewById(R.id.rv_height_scroll_selector);
        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);

        //setting adapter for height selection scale
        UnitScalesAdapter adapter = new UnitScalesAdapter(1700,this,this);
        recyclerView.setAdapter(adapter);

        //setting initial middle scale value;
        firstVisibleUnitOnScal = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
        lastVisibleUnitOnScal = linearLayoutManager.findLastCompletelyVisibleItemPosition();
        middleUnitOnScale = 1700;
        linearLayoutManager.scrollToPosition(1760);
        viewModel.setScaleSelectionValue(middleUnitOnScale);

        //on scrolling scale change scale text output
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //recyclerView.scrollToPosition(lastVisibleUnitOnScal);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleUnitOnScal = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                lastVisibleUnitOnScal = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                viewModel.setScaleSelectionValue(middleUnitOnScale);
                middleUnitOnScale = (firstVisibleUnitOnScal + lastVisibleUnitOnScal) / 2;
                adapter.middleUnit=(int)middleUnitOnScale;
          }
        });

        //updating ui here
        viewModel.getScaleSelectionValue().observe(this, new Observer<Float>() {
            @Override
            public void onChanged(Float val) {
                TextView tvScaleSelectedHeight = findViewById(R.id.tv_selected_height);
                tvScaleSelectedHeight.setText(String.valueOf(middleUnitOnScale / 10));
            }
        });

    }

    @Override
    public void onScaleUnitClicked(int scaleValue) {
        int offSetUnitsFromMiddleUnit = lastVisibleUnitOnScal - (int) middleUnitOnScale;
        if (scaleValue < middleUnitOnScale) {
            recyclerView.smoothScrollToPosition(scaleValue - offSetUnitsFromMiddleUnit );
        } else {
            recyclerView.smoothScrollToPosition(scaleValue + offSetUnitsFromMiddleUnit);
        }
        middleUnitOnScale = scaleValue;
        viewModel.setScaleSelectionValue(middleUnitOnScale);
    }
}