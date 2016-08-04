package com.yramnna.expenseapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.yramnna.expenseapp.R;
import com.yramnna.expenseapp.core.ExpenseApplication;

/**
 * Created by Rufo on 8/1/2016.
 */
public class BaseActivity extends AppCompatActivity {

    ExpenseApplication expenseApplication = ExpenseApplication.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setCurrentContext(Context context) {
        expenseApplication.setCurrentContext(context);
    }

    public void initializeToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ExpenseApplication.activityResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ExpenseApplication.activityPaused();
    }

}
