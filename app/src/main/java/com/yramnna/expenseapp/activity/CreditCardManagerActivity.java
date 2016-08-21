package com.yramnna.expenseapp.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.yramnna.expenseapp.R;
import com.yramnna.expenseapp.core.CreditTransactionType;
import com.yramnna.expenseapp.core.ExpenseApplication;
import com.yramnna.expenseapp.core.InstallmentAdapter;
import com.yramnna.expenseapp.core.PaymentType;
import com.yramnna.expenseapp.model.InstallmentCheckbox;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Rufo on 8/13/2016.
 */
public class CreditCardManagerActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, TextView.OnEditorActionListener {

    private ExpenseApplication expenseApplication;

    private Button btnAddTransaction;
    private Dialog addTransDialog;
    private TableRow trPaymentType;
    private TableRow trTranAmount;
    private TableRow trPrincipalAmount;
    private TableRow trMonthlyAmortization;
    private TableRow trMonthsToPay;
    private TableRow trUntil;
    private TableRow trInclude;
    private EditText etTranDate;
    private AutoCompleteTextView acTvTranDesc;
    private EditText etTranAmount;
    private EditText etPrincipalAmount;
    private EditText etMonthlyAmortization;
    private EditText etMonthsToPay;
    private EditText etUntil;
    private Spinner spTranType;
    private Spinner spPaymentType;
    private ListView lvInstallments;

    private InstallmentAdapter installmentAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cc_mngr);

        init();

        super.initializeToolbar();
        super.setCurrentContext(this);
    }

    private void init() {
        expenseApplication = ExpenseApplication.getInstance();
        btnAddTransaction = (Button) findViewById(R.id.btn_cc_add_transaction);
    }

    public void displayAddTransactionDialog(View view) {
        addTransDialog = new Dialog(this);
        addTransDialog.setContentView(R.layout.dialog_cc_mngr_add);
        addTransDialog.setTitle(getString(R.string.title_add_transaction));
        addTransDialog.setCancelable(false);
        addTransDialog.show();

        //setup dialog components
        trPaymentType = (TableRow) addTransDialog.findViewById(R.id.tr_cc_paymentType);
        trTranAmount = (TableRow) addTransDialog.findViewById(R.id.tr_cc_tranAmount);
        trPrincipalAmount = (TableRow) addTransDialog.findViewById(R.id.tr_cc_principalAmount);
        trMonthlyAmortization = (TableRow) addTransDialog.findViewById(R.id.tr_cc_monthlyAmortization);
        trMonthsToPay = (TableRow) addTransDialog.findViewById(R.id.tr_cc_monthsToPay);
        trUntil = (TableRow) addTransDialog.findViewById(R.id.tr_cc_until);
        trInclude = (TableRow) addTransDialog.findViewById(R.id.tr_cc_include);
        etTranDate = (EditText) addTransDialog.findViewById(R.id.et_cc_tranDate);
        acTvTranDesc = (AutoCompleteTextView) addTransDialog.findViewById(R.id.at_cc_tranDesc);
        etTranAmount = (EditText) addTransDialog.findViewById(R.id.et_cc_TranAmount);
        etPrincipalAmount = (EditText) addTransDialog.findViewById(R.id.et_cc_principalAmount);
        etMonthlyAmortization = (EditText) addTransDialog.findViewById(R.id.et_cc_monthlyAmortization);
        etMonthsToPay = (EditText) addTransDialog.findViewById(R.id.et_cc_monthsToPay);
        etUntil = (EditText) addTransDialog.findViewById(R.id.et_cc_until);
        spTranType = (Spinner) addTransDialog.findViewById(R.id.sp_cc_tranType);
        spPaymentType = (Spinner) addTransDialog.findViewById(R.id.sp_cc_paymentType);
        lvInstallments = (ListView) addTransDialog.findViewById(R.id.lv_cc_installments);

        //setup initial value
        etTranDate.setInputType(InputType.TYPE_NULL);
        etTranDate.setText(DateFormat.format(getString(R.string.date_format), Calendar.getInstance().getTime()));

        // setup listener
        etMonthsToPay.setOnEditorActionListener(this);
        spTranType.setOnItemSelectedListener(this);
        spPaymentType.setOnItemSelectedListener(this);

        // setup adapters
        acTvTranDesc.setAdapter(new ArrayAdapter<>(this, R.layout.cc_description_info, R.id.tv_cc_description, getResources().getStringArray(R.array.arr_cc_desc)));
    }

    public void handleCancel(View view) {
        addTransDialog.dismiss();
    }

    public void addTransaction(View view) {
        String responseText = "";
        ArrayList<InstallmentCheckbox> countryList = installmentAdapter.getInstallmentList();
        for(int i=0;i<countryList.size();i++){
            InstallmentCheckbox country = countryList.get(i);
            if(country.isSelected()){
                responseText += country.getName();
            }
        }

        Toast.makeText(getApplicationContext(),
                responseText, Toast.LENGTH_LONG).show();
    }

    public void displayCalendar(View view) {
        final Calendar currentCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                CreditCardManagerActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        currentCalendar.set(Calendar.YEAR, year);
                        currentCalendar.set(Calendar.MONTH, monthOfYear);
                        currentCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        etTranDate.setText(DateFormat.format(getString(R.string.date_format), currentCalendar.getTime()));
                    }
                },
                currentCalendar.get(Calendar.YEAR),
                currentCalendar.get(Calendar.MONTH),
                currentCalendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Object item = parent.getItemAtPosition(position);

        switch (parent.getId()) {
            case R.id.sp_cc_tranType:
                CreditTransactionType selectedTranType = CreditTransactionType.valueOf(item.toString());
                trPaymentType.setVisibility(selectedTranType.compareTo(CreditTransactionType.PAYMENT) == 0 ? View.VISIBLE : View.GONE);
                trTranAmount.setVisibility(selectedTranType.compareTo(CreditTransactionType.INSTALLMENT) == 0 ? View.GONE : View.VISIBLE);
                trPrincipalAmount.setVisibility(selectedTranType.compareTo(CreditTransactionType.INSTALLMENT) == 0 ? View.VISIBLE : View.GONE);
                trMonthlyAmortization.setVisibility(selectedTranType.compareTo(CreditTransactionType.INSTALLMENT) == 0 ? View.VISIBLE : View.GONE);
                trMonthsToPay.setVisibility(selectedTranType.compareTo(CreditTransactionType.INSTALLMENT) == 0 ? View.VISIBLE : View.GONE);
                trUntil.setVisibility(selectedTranType.compareTo(CreditTransactionType.INSTALLMENT) == 0 ? View.VISIBLE : View.GONE);
                trInclude.setVisibility(selectedTranType.compareTo(CreditTransactionType.PAYMENT) == 0 ? View.VISIBLE : View.GONE);

                if (selectedTranType.compareTo(CreditTransactionType.PAYMENT) == 0) {
                    acTvTranDesc.setText(spPaymentType.getSelectedItem().toString());
                    acTvTranDesc.setEnabled(false);

                    installmentAdapter = new InstallmentAdapter(this, R.layout.cc_installment_info, getInstallmentList());
                    lvInstallments.setAdapter(installmentAdapter);
                } else {
                    acTvTranDesc.setText(StringUtils.EMPTY);
                    acTvTranDesc.setEnabled(true);
                }
                break;

            case R.id.sp_cc_paymentType:
                PaymentType selectedPaymentType = PaymentType.valueOf(item.toString());
                if (selectedPaymentType.compareTo(PaymentType.Others) == 0) {
                    acTvTranDesc.setText(StringUtils.EMPTY);
                    acTvTranDesc.setEnabled(true);
                } else {
                    acTvTranDesc.setText(selectedPaymentType.name());
                    acTvTranDesc.setEnabled(false);
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // intentionally blank
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (v.getId()) {
            case R.id.et_cc_monthsToPay:
                etMonthlyAmortization.setText(
                        computeMonthlyAmortization(new BigDecimal(etPrincipalAmount.getText().toString()), new BigDecimal(etMonthsToPay.getText().toString())));
                etUntil.setText(
                        computeUntil(LocalDate.parse(etTranDate.getText().toString(), DateTimeFormat.forPattern(getString(R.string.date_format))), Integer.valueOf(etMonthsToPay.getText().toString())));
                break;
            default:
                break;
        }
        return false;
    }

    private ArrayList<InstallmentCheckbox> getInstallmentList() {
        ArrayList<InstallmentCheckbox> installmentList = new ArrayList<>();
        installmentList.add(new InstallmentCheckbox("Computer", false));
        installmentList.add(new InstallmentCheckbox("Desktop", false));
        installmentList.add(new InstallmentCheckbox("Cellphone", false));

        return installmentList;
    }

    private String computeMonthlyAmortization(BigDecimal principalAmount, BigDecimal monthsToPay) {
        return String.valueOf(principalAmount.divide(monthsToPay, 2, BigDecimal.ROUND_HALF_UP));
    }

    private String computeUntil(LocalDate tranDate, int monthsToPay) {
        return tranDate.plusMonths(monthsToPay).toString(getString(R.string.date_format));
    }
}
