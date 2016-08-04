package com.yramnna.expenseapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yramnna.expenseapp.R;
import com.yramnna.expenseapp.core.ExpenseApplication;
import com.yramnna.expenseapp.util.ValidationUtil;

public class LoginActivity extends AppCompatActivity {

    private ExpenseApplication expenseApplication;

    private TextView tvError;
    private EditText etPassCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity();
    }

    private void initializeActivity(){
        expenseApplication = ExpenseApplication.getInstance();

        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvError = (TextView) findViewById(R.id.tvError);
        etPassCode = (EditText) findViewById(R.id.etPassCode);
    }

    public  void attemptLogin(View view) {
        boolean isEmpty = ValidationUtil.validateIsEmpty(etPassCode);

        if (!isEmpty) {
            boolean isEqual = ValidationUtil.validateIsEqual(etPassCode, expenseApplication.getDefault());

            if (isEqual) {
                etPassCode.setText("");
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
            }
        }
    }

}
