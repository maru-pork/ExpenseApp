package com.yramnna.expenseapp.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.Toast;

import com.yramnna.expenseapp.R;
import com.yramnna.expenseapp.core.CreditTransactionType;
import com.yramnna.expenseapp.core.ExpenseApplication;
import com.yramnna.expenseapp.core.InstallmentAdapter;
import com.yramnna.expenseapp.core.PaymentType;
import com.yramnna.expenseapp.model.InstallmentCheckbox;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rufo on 8/13/2016.
 */
public class CreditCardManagerActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

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
    private EditText etTranDesc;
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

        trPaymentType = (TableRow) addTransDialog.findViewById(R.id.tr_cc_paymentType);
        trTranAmount = (TableRow) addTransDialog.findViewById(R.id.tr_cc_tranAmount);
        trPrincipalAmount = (TableRow) addTransDialog.findViewById(R.id.tr_cc_principalAmount);
        trMonthlyAmortization = (TableRow) addTransDialog.findViewById(R.id.tr_cc_monthlyAmortization);
        trMonthsToPay = (TableRow) addTransDialog.findViewById(R.id.tr_cc_monthsToPay);
        trUntil = (TableRow) addTransDialog.findViewById(R.id.tr_cc_until);
        trInclude = (TableRow) addTransDialog.findViewById(R.id.tr_cc_include);
        etTranDate = (EditText) addTransDialog.findViewById(R.id.et_cc_tranDate);
        etTranDesc = (EditText) addTransDialog.findViewById(R.id.et_cc_tranDesc);
        etTranAmount = (EditText) addTransDialog.findViewById(R.id.et_cc_TranAmount);
        etPrincipalAmount = (EditText) addTransDialog.findViewById(R.id.et_cc_principalAmount);
        etMonthlyAmortization = (EditText) addTransDialog.findViewById(R.id.et_cc_monthlyAmortization);
        etMonthsToPay = (EditText) addTransDialog.findViewById(R.id.et_cc_monthsToPay);
        etUntil = (EditText) addTransDialog.findViewById(R.id.et_cc_until);
        spTranType = (Spinner) addTransDialog.findViewById(R.id.sp_cc_tranType);
        spPaymentType = (Spinner) addTransDialog.findViewById(R.id.sp_cc_paymentType);
        lvInstallments = (ListView) addTransDialog.findViewById(R.id.lv_cc_installments);

        installmentAdapter = new InstallmentAdapter(this, R.layout.cc_installment_info, getInstallmentList());
        lvInstallments.setAdapter(installmentAdapter);

        spTranType.setOnItemSelectedListener(this);
        spPaymentType.setOnItemSelectedListener(this);
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
                    etTranDesc.setText(spPaymentType.getSelectedItem().toString());
                    etTranDesc.setEnabled(false);
                } else {
                    etTranDesc.setText(StringUtils.EMPTY);
                    etTranDesc.setEnabled(true);
                }
                break;

            case R.id.sp_cc_paymentType:
                PaymentType selectedPaymentType = PaymentType.valueOf(item.toString());
                if (selectedPaymentType.compareTo(PaymentType.Others) == 0) {
                    etTranDesc.setText(StringUtils.EMPTY);
                    etTranDesc.setEnabled(true);
                } else {
                    etTranDesc.setText(selectedPaymentType.name());
                    etTranDesc.setEnabled(false);
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

    private ArrayList<InstallmentCheckbox> getInstallmentList() {
        ArrayList<InstallmentCheckbox> installmentList = new ArrayList<>();
        installmentList.add(new InstallmentCheckbox("Computer", false));
        installmentList.add(new InstallmentCheckbox("Desktop", false));

        return installmentList;
    }
}
